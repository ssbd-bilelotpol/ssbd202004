package pl.lodz.p.it.ssbd2020.ssbd04.security;

import pl.lodz.p.it.ssbd2020.ssbd04.exceptions.AccountException;
import pl.lodz.p.it.ssbd2020.ssbd04.exceptions.AppBaseException;
import pl.lodz.p.it.ssbd2020.ssbd04.exceptions.ErrorResponse;
import pl.lodz.p.it.ssbd2020.ssbd04.mok.endpoints.AccountEndpoint;

import javax.annotation.security.RolesAllowed;
import javax.inject.Inject;
import javax.security.enterprise.SecurityContext;
import javax.security.enterprise.credential.UsernamePasswordCredential;
import javax.security.enterprise.identitystore.CredentialValidationResult;
import javax.security.enterprise.identitystore.IdentityStoreHandler;
import javax.servlet.http.HttpServletRequest;
import javax.validation.constraints.NotNull;
import javax.ws.rs.*;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.time.LocalDateTime;
import java.util.logging.Level;
import java.util.logging.Logger;

import static javax.security.enterprise.identitystore.CredentialValidationResult.Status.VALID;
import static javax.ws.rs.core.Response.Status.FORBIDDEN;
import static javax.ws.rs.core.Response.Status.UNAUTHORIZED;
import static pl.lodz.p.it.ssbd2020.ssbd04.common.I18n.AUTH_INCORRECT_LOGIN_OR_PASSWORD;
import static pl.lodz.p.it.ssbd2020.ssbd04.security.JWTAuthenticationMechanism.AUTHORIZATION_HEADER;
import static pl.lodz.p.it.ssbd2020.ssbd04.security.JWTAuthenticationMechanism.extractToken;
import static pl.lodz.p.it.ssbd2020.ssbd04.security.Role.*;

@Path("/auth")
/**
 * Zasób zajmujący się uwierzytelnianiem klienta na podstawie danych uwierzytelniających.
 * Po poprawnym uwierzytelnieniu zwracany jest JWT.
 */
public class AuthResource {
    private static final Logger LOGGER = Logger.getLogger(AuthResource.class.getName());

    @Inject
    private SecurityContext securityContext;

    @Inject
    private IdentityStoreHandler identityStoreHandler;

    @Inject
    private AccountEndpoint accountEndpoint;

    @Inject
    private JWTProvider jwtProvider;

    @Context
    private HttpServletRequest httpServletRequest;

    /**
     * Obsługuje uwierzytelnianie.
     *
     * @param loginData Obiekt, który jest deserializowany na podstawie danych w formacie JSON od klienta.
     * @return Gdy uwierzytelnienie się powiedzie, zwraca kod odpowiedzi 200 i token JWT.
     * W przeciwnym wypadku zwraca 401 Unauthorized.
     */
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response auth(LoginData loginData) throws AppBaseException {
        CredentialValidationResult result = identityStoreHandler.validate(loginData.toCredential());
        if (result.getStatus() != VALID) {
            accountEndpoint.updateAuthInfo(loginData.username, LocalDateTime.now());
            return Response
                    .status(UNAUTHORIZED)
                    .entity(new ErrorResponse(AUTH_INCORRECT_LOGIN_OR_PASSWORD))
                    .build();
        }
        try {
            accountEndpoint.updateAuthInfo(loginData.username, httpServletRequest.getRemoteAddr(), LocalDateTime.now());
        } catch (AccountException e) {
            return Response
                    .status(FORBIDDEN)
                    .entity(new ErrorResponse(e.getMessage()))
                    .build();
        }
        LOGGER.log(Level.INFO, "User {0} logged in with IP {1}",
                new Object[]{loginData.username, httpServletRequest.getRemoteAddr()});
        if (result.getCallerGroups().contains("admin")) {
            accountEndpoint.notifyAboutAdminLogin(loginData.username, httpServletRequest.getRemoteAddr());
        }
        return Response.ok().entity(jwtProvider.create(result)).build();
    }

    @POST
    @Path("/change-role/{role}")
    @Consumes(MediaType.APPLICATION_JSON)
    @RolesAllowed({ChangeRole})
    public Response changeRole(@PathParam("role") String group) {
        String role = GroupRoleMapper.get(group);
        if (securityContext.isCallerInRole(role)) {
            LOGGER.log(Level.INFO, "User {0} with IP {1} changed role to {2}",
                    new Object[]{securityContext.getCallerPrincipal().getName(), httpServletRequest.getRemoteAddr(), role});
            return Response.ok().build();
        } else {
            LOGGER.log(Level.WARNING, "User {0} with IP {1} tried changing role to {2}",
                    new Object[]{securityContext.getCallerPrincipal().getName(), httpServletRequest.getRemoteAddr(), role});
            return Response.status(UNAUTHORIZED).build();
        }
    }

    @POST
    @Path("/refresh-token")
    @RolesAllowed({RefreshToken})
    public Response refreshToken(ContainerRequestContext requestContext) {
        String currentToken = extractToken(requestContext.getHeaderString(AUTHORIZATION_HEADER));
        JWT refreshedToken = jwtProvider.refresh(currentToken);
        LOGGER.log(Level.INFO, "User {0} with IP {1} refreshed token",
                new Object[]{securityContext.getCallerPrincipal().getName(), httpServletRequest.getRemoteAddr()});
        return Response.ok().entity(refreshedToken).build();
    }

    /**
     * Klasa przenosząca dane uwierzytelniające.
     */
    public static class LoginData {
        @NotNull
        private String username;
        @NotNull
        private String password;

        public void setUsername(String username) {
            this.username = username;
        }

        public void setPassword(String password) {
            this.password = password;
        }

        private UsernamePasswordCredential toCredential() {
            return new UsernamePasswordCredential(username, password);
        }
    }
}

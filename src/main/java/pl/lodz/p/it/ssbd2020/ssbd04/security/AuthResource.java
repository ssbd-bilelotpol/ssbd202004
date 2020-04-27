package pl.lodz.p.it.ssbd2020.ssbd04.security;

import pl.lodz.p.it.ssbd2020.ssbd04.exceptions.ErrorResponse;

import javax.annotation.security.RolesAllowed;
import javax.inject.Inject;
import javax.security.enterprise.SecurityContext;
import javax.security.enterprise.credential.UsernamePasswordCredential;
import javax.security.enterprise.identitystore.CredentialValidationResult;
import javax.security.enterprise.identitystore.IdentityStoreHandler;
import javax.validation.constraints.NotNull;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;

import static javax.security.enterprise.identitystore.CredentialValidationResult.Status.VALID;
import static javax.ws.rs.core.Response.Status.UNAUTHORIZED;
import static pl.lodz.p.it.ssbd2020.ssbd04.security.Role.GroupRoleMapper;
import static pl.lodz.p.it.ssbd2020.ssbd04.utils.I18n.AUTH_INCORRECT_LOGIN_OR_PASSWORD;

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
    private JWTProvider jwtProvider;

    /**
     * Obsługuje uwierzytelnianie.
     * @param loginData Obiekt, który jest deserializowany na podstawie danych w formacie JSON od klienta.
     * @return
     * Gdy uwierzytelnienie się powiedzie, zwraca kod odpowiedzi 200 i token JWT.
     * W przeciwnym wypadku zwraca 401 Unauthorized.
     */
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response auth(LoginData loginData) {
        CredentialValidationResult result = identityStoreHandler.validate(loginData.toCredential());
        if (result.getStatus() != VALID) {
            return Response
                    .status(UNAUTHORIZED)
                    .entity(new ErrorResponse(AUTH_INCORRECT_LOGIN_OR_PASSWORD))
                    .build();
        }

        return Response.ok().entity(jwtProvider.create(result)).build();
    }

    @POST
    @Path("/change-role/{role}")
    @Consumes(MediaType.APPLICATION_JSON)
    @RolesAllowed({Role.Admin, Role.Manager, Role.Client, Role.CustomerService})
    public Response changeRole(@PathParam("role") String group) {
        String role = GroupRoleMapper.get(group);
        if (securityContext.isCallerInRole(role)) {
            LOGGER.log(Level.INFO, "User " + securityContext.getCallerPrincipal().getName() + " changed role to " + role);
            return Response.ok().build();
        } else {
            LOGGER.log(Level.WARNING, "User " + securityContext.getCallerPrincipal().getName() + " tried changing role to " + role);
            return Response.status(UNAUTHORIZED).build();
        }
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

package pl.lodz.p.it.ssbd2020.ssbd04.security;

import javax.inject.Inject;
import javax.security.enterprise.credential.UsernamePasswordCredential;
import javax.security.enterprise.identitystore.CredentialValidationResult;
import javax.security.enterprise.identitystore.IdentityStoreHandler;
import javax.validation.constraints.NotNull;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import static javax.security.enterprise.identitystore.CredentialValidationResult.Status.VALID;

@Path("/auth")
public class AuthResource {

    @Inject
    private IdentityStoreHandler identityStoreHandler;

    @Inject
    private JWTProvider jwtProvider;

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response auth(LoginData loginData) {
        CredentialValidationResult result = identityStoreHandler.validate(loginData.toCredential());
        if (result.getStatus() != VALID) {
            return Response.status(Response.Status.UNAUTHORIZED).build();
        }

        return Response.ok().entity(jwtProvider.create(result)).build();
    }

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

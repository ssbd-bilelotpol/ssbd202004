package pl.lodz.p.it.ssbd2020.ssbd04.resources;

import pl.lodz.p.it.ssbd2020.ssbd04.security.Role;

import javax.annotation.security.RolesAllowed;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;

@Path("/security")
public class SecurityResource {

    @GET
    @RolesAllowed({Role.Admin})
    @Path("/admin")
    public Response admin() {
        return Response
                .ok("pong")
                .build();
    }

    @GET
    @RolesAllowed({Role.Manager})
    @Path("/resourceManager")
    public Response resourceManager() {
        return Response
                .ok("pong")
                .build();
    }

    @GET
    @RolesAllowed({Role.CustomerService})
    @Path("/clientManager")
    public Response clientManager() {
        return Response
                .ok("pong")
                .build();
    }


    @GET
    @RolesAllowed({Role.Client})
    @Path("/client")
    public Response client() {
        return Response
                .ok("pong")
                .build();
    }
}

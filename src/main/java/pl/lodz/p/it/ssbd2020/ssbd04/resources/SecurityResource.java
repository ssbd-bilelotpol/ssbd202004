package pl.lodz.p.it.ssbd2020.ssbd04.resources;

import pl.lodz.p.it.ssbd2020.ssbd04.security.Role;

import javax.annotation.security.RolesAllowed;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;

/**
 *
 * @author 
 */
@Path("/security")
public class SecurityResource {
    
    @GET
    @RolesAllowed({Role.Admin})
    @Path("/admin")
    public Response admin(){
        return Response
                .ok("ping")
                .build();
    }

    @GET
    @RolesAllowed({Role.ResourceManager})
    @Path("/resourceManager")
    public Response resourceManager(){
        return Response
                .ok("ping")
                .build();
    }

    @GET
    @RolesAllowed({Role.ClientManager})
    @Path("/clientManager")
    public Response clientManager(){
        return Response
                .ok("ping")
                .build();
    }


    @GET
    @RolesAllowed({Role.Client})
    @Path("/client")
    public Response client(){
        return Response
                .ok("ping")
                .build();
    }
}

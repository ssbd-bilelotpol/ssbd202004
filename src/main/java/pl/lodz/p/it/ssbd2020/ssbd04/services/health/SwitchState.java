package pl.lodz.p.it.ssbd2020.ssbd04.services.health;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/switch-state")
@RequestScoped
public class SwitchState {


    @Inject
    private State state;

    @GET
    @Produces({MediaType.APPLICATION_JSON})
    public Response switchState() {
        state.switchState();
        return Response.ok(state.getState()).build();
    }
}

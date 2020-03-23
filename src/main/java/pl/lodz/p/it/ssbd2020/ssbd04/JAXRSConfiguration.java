package pl.lodz.p.it.ssbd2020.ssbd04;

import pl.lodz.p.it.ssbd2020.ssbd04.security.Role;

import javax.annotation.security.DeclareRoles;
import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;

/**
 * Configures JAX-RS for the application.
 * @author Juneau
 */
@ApplicationPath("api")
@DeclareRoles({Role.Admin, Role.ResourceManager, Role.ClientManager, Role.Client})
public class JAXRSConfiguration extends Application {
    
}

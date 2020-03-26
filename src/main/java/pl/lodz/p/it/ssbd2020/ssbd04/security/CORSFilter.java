package pl.lodz.p.it.ssbd2020.ssbd04.security;

import pl.lodz.p.it.ssbd2020.ssbd04.utils.Config;

import javax.inject.Inject;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerResponseContext;
import javax.ws.rs.container.ContainerResponseFilter;
import javax.ws.rs.ext.Provider;

@Provider
public class CORSFilter implements ContainerResponseFilter {
    @Inject
    Config config;

    @Override
    public void filter(final ContainerRequestContext requestContext,
                       final ContainerResponseContext cres) {
        cres.getHeaders().add("Access-Control-Allow-Origin", config.get(Config.FRONTEND_URL));
        cres.getHeaders().add("Access-Control-Allow-Headers", "*");
        cres.getHeaders().add("Access-Control-Allow-Credentials", "true");
        cres.getHeaders().add("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS, HEAD");
        cres.getHeaders().add("Access-Control-Max-Age", "1209600");
    }
}

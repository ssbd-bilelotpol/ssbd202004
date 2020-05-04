package pl.lodz.p.it.ssbd2020.ssbd04.security;

import pl.lodz.p.it.ssbd2020.ssbd04.common.Config;

import javax.inject.Inject;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerResponseContext;
import javax.ws.rs.container.ContainerResponseFilter;
import javax.ws.rs.ext.Provider;

/**
 * Do każdej odpowiedzi HTTP dodaje nagłówki CORS dla serwera podanego w obiekcie konfiguracyjnym.
 */
@Provider
public class CORSFilter implements ContainerResponseFilter {
    @Inject
    private Config config;

    @Override
    public void filter(final ContainerRequestContext requestContext,
                       final ContainerResponseContext cres) {
        cres.getHeaders().add("Access-Control-Allow-Origin", config.getFrontendURL());
        cres.getHeaders().add("Access-Control-Allow-Headers", "*");
        cres.getHeaders().add("Access-Control-Allow-Credentials", "true");
        cres.getHeaders().add("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS, HEAD");
        cres.getHeaders().add("Access-Control-Max-Age", "1209600");
        cres.getHeaders().add("Access-Control-Expose-Headers", "ETag");
    }
}

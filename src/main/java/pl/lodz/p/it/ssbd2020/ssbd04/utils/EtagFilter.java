package pl.lodz.p.it.ssbd2020.ssbd04.utils;

import pl.lodz.p.it.ssbd2020.ssbd04.security.MessageSigner;

import javax.inject.Inject;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.Provider;

/**
 * Filtr, który sprawdza czy zapytanie posiada poprawny nagłówek odpowiedzialny za kontrole wersji
 */
@Provider
@EtagBinding
public class EtagFilter implements ContainerRequestFilter {

    @Inject
    private MessageSigner messageSigner;


    @Override
    public void filter(ContainerRequestContext requestContext) {
        String header = requestContext.getHeaderString("If-None-Match");
        if (header == null || header.isEmpty() || !header.contains(".")) {
            requestContext.abortWith(Response.status(Response.Status.BAD_REQUEST)
                    .entity(I18n.ETAG_WRONG_VALUE)
                    .build());
            return;
        }
        header = header.replace("\"", "");
        String message = header.split("\\.")[0];
        String hmac = header.split("\\.")[1];
        String encodedMessage = messageSigner.sign(message);
        if (!encodedMessage.equals(hmac)) {
            requestContext.abortWith(Response.status(Response.Status.BAD_REQUEST)
                    .entity(I18n.ETAG_WRONG_VALUE)
                    .build());
        }
    }
}

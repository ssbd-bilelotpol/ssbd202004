package pl.lodz.p.it.ssbd2020.ssbd04.security;

import pl.lodz.p.it.ssbd2020.ssbd04.common.I18n;
import pl.lodz.p.it.ssbd2020.ssbd04.exceptions.ErrorResponse;

import javax.inject.Inject;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
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

    @Inject
    private HeaderContainer headerContainer;

    @Context
    private HttpHeaders httpHeaders;

    @Override
    public void filter(ContainerRequestContext requestContext) {
        headerContainer.setHeaders(httpHeaders);
        String header = requestContext.getHeaderString("If-Match");
        if (header == null || header.isEmpty() || !header.contains(".")) {
            requestContext.abortWith(Response.status(Response.Status.BAD_REQUEST)
                    .entity(new ErrorResponse(I18n.ETAG_WRONG_VALUE))
                    .build());
            return;
        }
        header = header.replace("\"", "");
        String message = header.split("\\.")[0];
        String hmac = header.split("\\.")[1];
        String encodedMessage = messageSigner.sign(message);
        if (!encodedMessage.equals(hmac)) {
            requestContext.abortWith(Response.status(Response.Status.BAD_REQUEST)
                    .entity(new ErrorResponse(I18n.ETAG_WRONG_VALUE))
                    .build());
        }
    }
}

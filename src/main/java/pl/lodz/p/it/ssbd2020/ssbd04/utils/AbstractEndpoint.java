package pl.lodz.p.it.ssbd2020.ssbd04.utils;

import pl.lodz.p.it.ssbd2020.ssbd04.security.MessageSigner;
import pl.lodz.p.it.ssbd2020.ssbd04.security.Signable;

import javax.inject.Inject;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;

/**
 * Klasa abstrakcyjna definująca sposób sprawdzania wersji dostarczanego zasobu do endpointa
 */
public class AbstractEndpoint {

    @Inject
    protected MessageSigner messageSigner;

    @Context
    private HttpHeaders httpHeaders;

    protected boolean verifyEtag(Signable signable) {
        String sent = httpHeaders.getRequestHeader("If-Match").get(0).replaceAll("\"", "");
        String expected = messageSigner.sign(signable);
        return sent.equals(expected);
    }
}

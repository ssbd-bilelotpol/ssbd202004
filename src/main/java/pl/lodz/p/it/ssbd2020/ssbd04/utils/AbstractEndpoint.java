package pl.lodz.p.it.ssbd2020.ssbd04.utils;

import pl.lodz.p.it.ssbd2020.ssbd04.security.MessageSigner;
import pl.lodz.p.it.ssbd2020.ssbd04.security.Signable;

import javax.inject.Inject;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Request;

/**
 * Klasa abstrakcyjna definująca sposób sprawdzania wersji dostarczanego zasobu do endpointa
 */
public class AbstractEndpoint {

    @Inject
    protected MessageSigner messageSigner;

    @Context
    protected Request request;

    protected boolean verifyEtag(Signable signable) {
        return request.evaluatePreconditions(messageSigner.sign(signable)) != null;
    }
}

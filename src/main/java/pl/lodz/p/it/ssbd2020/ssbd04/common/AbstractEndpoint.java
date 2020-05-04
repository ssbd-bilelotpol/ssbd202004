package pl.lodz.p.it.ssbd2020.ssbd04.common;

import pl.lodz.p.it.ssbd2020.ssbd04.security.MessageSigner;
import pl.lodz.p.it.ssbd2020.ssbd04.security.Signable;

import javax.ejb.AfterBegin;
import javax.ejb.AfterCompletion;
import javax.ejb.BeforeCompletion;
import javax.inject.Inject;
import javax.security.enterprise.SecurityContext;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import java.security.Principal;
import java.util.concurrent.ThreadLocalRandom;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Klasa abstrakcyjna definująca sposób sprawdzania wersji dostarczanego zasobu do endpointa
 */
public abstract class AbstractEndpoint {

    @Inject
    protected MessageSigner messageSigner;

    @Context
    private HttpHeaders httpHeaders;

    @Inject
    private SecurityContext securityContext;

    private final Logger LOGGER = Logger.getLogger(getClass().getName());
    private String transactionId;
    private boolean lastTransactionRollback;

    protected boolean verifyEtag(Signable signable) {
        String sent = httpHeaders.getRequestHeader("If-Match").get(0).replaceAll("\"", "");
        String expected = messageSigner.sign(signable);
        return sent.equals(expected);
    }

    public boolean isLastTransactionRollback() {
        return lastTransactionRollback;
    }

    @AfterBegin
    public void afterBegin() {
        transactionId = Long.toString(System.currentTimeMillis())
                + ThreadLocalRandom.current().nextLong(Long.MAX_VALUE);
        LOGGER.log(Level.INFO, "Transaction id={0} started in {1}, identity: {2}",
                new Object[]{transactionId, this.getClass().getName(), getIdentity()});
    }

    @BeforeCompletion
    public void beforeCompletion() {
        LOGGER.log(Level.INFO, "Transaction id={0} before completion in {1}, identity: {2}",
                new Object[]{transactionId, this.getClass().getName(), getIdentity()});
    }

    @AfterCompletion
    public void afterCompletion(boolean committed) {
        lastTransactionRollback = !committed;
        LOGGER.log(Level.INFO, "Transaction id={0} ended in {1} with result: {3}, identity: {2}",
                new Object[]{transactionId, this.getClass().getName(), getIdentity(),
                        committed ? "COMMITED" : "ROLLBACK"});
    }

    private String getIdentity() {
        Principal principal = securityContext.getCallerPrincipal();
        return principal != null ? principal.getName() : "unauthorized";
    }
}

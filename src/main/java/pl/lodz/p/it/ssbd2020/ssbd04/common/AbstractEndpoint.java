package pl.lodz.p.it.ssbd2020.ssbd04.common;

import pl.lodz.p.it.ssbd2020.ssbd04.security.HeaderContainer;
import pl.lodz.p.it.ssbd2020.ssbd04.security.MessageSigner;
import pl.lodz.p.it.ssbd2020.ssbd04.security.Signable;

import javax.annotation.security.PermitAll;
import javax.ejb.AfterBegin;
import javax.ejb.AfterCompletion;
import javax.inject.Inject;
import javax.security.enterprise.SecurityContext;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import java.security.Principal;
import java.util.concurrent.ThreadLocalRandom;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Klasa abstrakcyjna definująca sposób sprawdzania wersji dostarczanego zasobu do endpointa.
 */
public abstract class AbstractEndpoint {

    @Inject
    protected MessageSigner messageSigner;

    @Inject
    private HeaderContainer headerContainer;

    @Inject
    private SecurityContext securityContext;

    private final Logger LOGGER = Logger.getLogger(getClass().getName());
    private String transactionId;
    private boolean lastTransactionRollback;

    /**
     * Weryfikuje poprawność nagłówka If-Match.
     *
     * @param signable obiekt typu Signable.
     * @return wynik weryfikacji.
     */
    protected boolean verifyEtag(Signable signable) {
        String sent = headerContainer.getHeaders().getRequestHeader("If-Match").get(0).replaceAll("\"", "");
        String expected = messageSigner.sign(signable);
        return sent.equals(expected);
    }

    /**
     * Po zakończeniu transakcji, umożliwia odpytanie o jej status.
     *
     * @return true, jeśli transakcja została odwołana, w przeciwnym przypadku false.
     */
    @PermitAll
    public boolean isLastTransactionRollback() {
        return lastTransactionRollback;
    }

    /**
     * Metoda, która wykonuje się przed rozpoczęciem transakcji.
     */
    @AfterBegin
    public void afterBegin() {
        transactionId = Long.toString(System.currentTimeMillis())
                + ThreadLocalRandom.current().nextLong(Long.MAX_VALUE);
        LOGGER.log(Level.INFO, "Transaction id={0} started in {1}, identity: {2}",
                new Object[]{transactionId, this.getClass().getName(), getIdentity()});
    }

    /**
     * Metoda, która wykonuje się po zakończeniu transakcji.
     *
     * @param committed zwraca, czy transakcja została zatwierdzona.
     */
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

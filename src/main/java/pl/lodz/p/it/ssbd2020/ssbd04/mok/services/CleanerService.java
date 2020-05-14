package pl.lodz.p.it.ssbd2020.ssbd04.mok.services;

import pl.lodz.p.it.ssbd2020.ssbd04.entities.verification_tokens.RegisterToken;
import pl.lodz.p.it.ssbd2020.ssbd04.entities.verification_tokens.VerificationToken;
import pl.lodz.p.it.ssbd2020.ssbd04.exceptions.AppBaseException;
import pl.lodz.p.it.ssbd2020.ssbd04.mok.facades.AccountFacade;
import pl.lodz.p.it.ssbd2020.ssbd04.mok.facades.VerificationTokenFacade;

import javax.ejb.*;
import javax.inject.Inject;
import java.time.LocalDateTime;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Odpowiada za czyszczenie niepotrzebnych żetonów weryfikujących z bazy danych.
 */

@Startup
@Singleton
public class CleanerService {
    private static final Logger LOGGER = Logger.getLogger(CleanerService.class.getName());

    @Inject
    private VerificationTokenFacade verificationTokenFacade;

    @Inject
    private AccountFacade accountFacade;

    /**
     * Zadanie uruchamiane codziennie o północy, które usuwa wygasłe żetony.
     * Jeśli żeton jest używany do potwierdzenia rejestracji, usuwa wraz z nim konto.
     *
     * @param timer
     */
    @Schedule
    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    private void clearVerificationTokens(Timer timer) {
        LOGGER.log(Level.INFO, "Clearing VerificationToken {0}", timer.getInfo().toString());
        try {
            verificationTokenFacade.findExpiredBefore(LocalDateTime.now()).forEach(this::removeToken);
        } catch (AppBaseException e) {
            LOGGER.log(Level.WARNING, "Failed to clear verification tokens");
        }
    }

    private void removeToken(VerificationToken verificationToken) {
        try {
            verificationTokenFacade.remove(verificationToken);
            LOGGER.log(Level.INFO, "Removed token {0}", verificationToken.toString());
            if (verificationToken instanceof RegisterToken) {
                accountFacade.remove(verificationToken.getAccount());
                LOGGER.log(Level.INFO, "Removed account connected to RegisterToken", verificationToken.getAccount());
            }
        } catch (AppBaseException e) {
            LOGGER.log(Level.WARNING, "Failed to remove token {0}", verificationToken.toString());
        }
    }
}

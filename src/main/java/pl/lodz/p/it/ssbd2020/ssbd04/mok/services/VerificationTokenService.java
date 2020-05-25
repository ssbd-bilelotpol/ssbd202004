package pl.lodz.p.it.ssbd2020.ssbd04.mok.services;

import pl.lodz.p.it.ssbd2020.ssbd04.common.Config;
import pl.lodz.p.it.ssbd2020.ssbd04.common.I18n;
import pl.lodz.p.it.ssbd2020.ssbd04.entities.Account;
import pl.lodz.p.it.ssbd2020.ssbd04.entities.verification_tokens.RegisterToken;
import pl.lodz.p.it.ssbd2020.ssbd04.entities.verification_tokens.ResetPasswordToken;
import pl.lodz.p.it.ssbd2020.ssbd04.entities.verification_tokens.VerificationToken;
import pl.lodz.p.it.ssbd2020.ssbd04.exceptions.AppBaseException;
import pl.lodz.p.it.ssbd2020.ssbd04.exceptions.VerificationTokenException;
import pl.lodz.p.it.ssbd2020.ssbd04.interceptors.TrackingInterceptor;
import pl.lodz.p.it.ssbd2020.ssbd04.mok.facades.VerificationTokenFacade;
import pl.lodz.p.it.ssbd2020.ssbd04.services.EmailService;

import javax.annotation.security.PermitAll;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;
import javax.interceptor.Interceptors;
import java.time.LocalDateTime;
import java.util.UUID;

/**
 * Przetwarzanie logiki biznesowej żetonów weryfikujących.
 */

@Interceptors({TrackingInterceptor.class})
@Stateless
@TransactionAttribute(TransactionAttributeType.MANDATORY)
@PermitAll
public class VerificationTokenService {
    @Inject
    private VerificationTokenFacade verificationTokenFacade;

    @Inject
    private Config config;

    @Inject
    private I18n i18n;

    @Inject
    private EmailService emailService;

    /**
     * Tworzy nowy żeton potwierdzenia rejestracji i wysyła go na e-mail odpowiadający kontu.
     *
     * @param account nowo zarejestrowane konto
     * @throws AppBaseException w przypadku niepowodzenia operacji
     */
    public void sendRegisterToken(Account account) throws AppBaseException {
        RegisterToken registerToken = new RegisterToken(LocalDateTime.now().plusDays(1), account);
        verificationTokenFacade.create(registerToken);
        try {
            emailService.sendEmail(account.getAccountDetails().getEmail(),
                    i18n.getMessage(I18n.ACCOUNT_REGISTRATION_MAIL_SENDER),
                    i18n.getMessage(I18n.ACCOUNT_REGISTER_MAIL_TITLE),
                    String.format(
                            "%s %s %s",
                            i18n.getMessage(I18n.ACCOUNT_REGISTER_MAIL_CONTENT),
                            String.format("%s/confirm/%s", config.getFrontendURL(), registerToken.getId().toString()),
                            i18n.getMessage(I18n.MAIL_FOOTER)
                    )
            );
        } catch (AppBaseException e) {
            throw VerificationTokenException.mailFailure(registerToken);
        }
    }

    /**
     * Weryfikuje poprawność tokenu rejestracji i usuwa go.
     * Nie jest sprawdzany czas wygaśnięcia tokenu,
     * by nie tworzyć okienka pomiędzy punktem kiedy wygasł, a kiedy zostanie usunięty.
     * Usuwanie jest dokonywane przez CleanerService.
     *
     * @param tokenId token potwierdzający
     * @return zarejestrowane konto
     * @throws AppBaseException gdy token wygasł, bądź konto zostało już potwierdzone.
     */
    public Account confirmRegistration(UUID tokenId) throws AppBaseException {
        VerificationToken verificationToken = verificationTokenFacade.find(tokenId.toString());
        if (!(verificationToken instanceof RegisterToken)) {
            throw VerificationTokenException.invalidRegisterToken(tokenId);
        }
        if (verificationToken.getAccount().getConfirm()) {
            verificationTokenFacade.remove(verificationToken);
            throw VerificationTokenException.accountAlreadyConfirmed(verificationToken);
        }
        verificationTokenFacade.remove(verificationToken);
        return verificationToken.getAccount();
    }

    /**
     * Sprawdza poprawność tokenu resetującego hasło. Jeżeli istnieje, jest poprawnego typu i nie wygasł,
     * zwraca powiązane z nim konto i usuwa go z bazy.
     *
     * @param tokenId token resetujący
     * @return konto powiązane z tokenem
     * @throws AppBaseException w przypadku niepowodzenia operacji
     */ 
    public Account confirmPasswordReset(UUID tokenId) throws AppBaseException {
        VerificationToken verificationToken = verificationTokenFacade.find(tokenId.toString());
        if (!(verificationToken instanceof ResetPasswordToken)) {
            throw VerificationTokenException.invalidRegisterToken(tokenId);
        }
        if (verificationToken.getExpireDateTime().isBefore(LocalDateTime.now())) {
            throw VerificationTokenException.expired(verificationToken);
        }
        verificationTokenFacade.remove(verificationToken);
        return verificationToken.getAccount();
    }

    /**
     * Tworzy nowy żeton resetu hasła i wysyła go na e-mail odpowiadający kontu.
     *
     * @param account - konto, którego hasło ma zostać zresetowane
     * @throws AppBaseException - jeżeli nie uda się wysłać maila
     */
    public void sendResetPasswordToken(Account account) throws AppBaseException {
        ResetPasswordToken resetPasswordToken = new ResetPasswordToken(LocalDateTime.now().plusMinutes(10), account);
        verificationTokenFacade.create(resetPasswordToken);
        try {
            emailService.sendEmail(account.getAccountDetails().getEmail(),
                    i18n.getMessage(I18n.ACCOUNT_PASSWORD_RESET_MAIL_SENDER),
                    i18n.getMessage(I18n.ACCOUNT_PASS_RESET_MAIL_TITLE),
                    String.format(
                            "%s %s %s",
                            i18n.getMessage(I18n.ACCOUNT_PASS_RESET_MAIL_CONTENT),
                            String.format("%s/resetPassword/%s", config.getFrontendURL(), resetPasswordToken.getId().toString()),
                            i18n.getMessage(I18n.MAIL_FOOTER)
                    )
            );
        } catch (AppBaseException e) {
            throw VerificationTokenException.mailFailure(resetPasswordToken);
        }
    }

}

package pl.lodz.p.it.ssbd2020.ssbd04.mok.services;

import kong.unirest.HttpResponse;
import kong.unirest.JsonNode;
import kong.unirest.Unirest;
import pl.lodz.p.it.ssbd2020.ssbd04.exceptions.AppBaseException;
import pl.lodz.p.it.ssbd2020.ssbd04.exceptions.VerificationTokenException;
import pl.lodz.p.it.ssbd2020.ssbd04.mok.entities.Account;
import pl.lodz.p.it.ssbd2020.ssbd04.mok.entities.verification_tokens.RegisterToken;
import pl.lodz.p.it.ssbd2020.ssbd04.mok.entities.verification_tokens.ResetPasswordToken;
import pl.lodz.p.it.ssbd2020.ssbd04.mok.entities.verification_tokens.VerificationToken;
import pl.lodz.p.it.ssbd2020.ssbd04.mok.facades.VerificationTokenFacade;
import pl.lodz.p.it.ssbd2020.ssbd04.utils.Config;
import pl.lodz.p.it.ssbd2020.ssbd04.utils.I18n;

import javax.annotation.security.PermitAll;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;
import java.time.LocalDateTime;
import java.util.UUID;

/**
 * Przetwarzanie logiki biznesowej żetonów weryfikujących.
 */
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

    /**
     * Tworzy nowy żeton potwierdzenia rejestracji i wysyła go na e-mail odpowiadający kontu.
     *
     * @param account
     * @throws AppBaseException
     */
    public void sendRegisterToken(Account account) throws AppBaseException {
        RegisterToken registerToken = new RegisterToken(LocalDateTime.now().plusDays(1), account);
        verificationTokenFacade.create(registerToken);
        sendEmail(registerToken,
                i18n.getMessage(I18n.ACCOUNT_REGISTRATION),
                i18n.getMessage(I18n.ACCOUNT_REGISTER_MAIL_TITLE),
                String.format("%s/confirm/%s", config.getFrontendURL(), registerToken.getId().toString()));
    }

    /**
     * Weryfikuje poprawność tokenu rejestracji i usuwa go.
     * Nie jest sprawdzany czas wygaśnięcia tokenu,
     * by nie tworzyć okienka pomiędzy punktem kiedy wygasł, a kiedy zostanie usunięty.
     * Usuwanie jest dokonywane przez CleanerService.
     *
     * @param tokenId
     * @return
     * @throws AppBaseException gdy token wygasł, bądź konto zostało już potwierdzone.
     */
    public Account confirmRegistration(UUID tokenId) throws AppBaseException {
        VerificationToken verificationToken = verificationTokenFacade.find(tokenId);
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
     * @param tokenId token
     * @return konto powiązane z tokenem
     * @throws AppBaseException w przypadku niepowodzenia operacji
     */
    public Account confirmPasswordReset(UUID tokenId) throws AppBaseException {
        VerificationToken verificationToken = verificationTokenFacade.find(tokenId);
        if (!(verificationToken instanceof ResetPasswordToken)) {
            throw VerificationTokenException.invalidRegisterToken(tokenId);
        }
        if(verificationToken.getExpireDateTime().isBefore(LocalDateTime.now())) {
            throw VerificationTokenException.expired(verificationToken);
        }
        verificationTokenFacade.remove(verificationToken);
        return verificationToken.getAccount();
    }

    /**
     * Tworzy nowy żeton resetu hasła i wysyła go na e-mail odpowiadający kontu.
     * @param account - konto, którego hasło ma zostać zresetowane
     * @throws AppBaseException - jeżeli nie uda się wysłać maila
     */
    public void sendResetPasswordToken(Account account) throws AppBaseException {
        ResetPasswordToken resetPasswordToken = new ResetPasswordToken(LocalDateTime.now().plusDays(1), account);
        verificationTokenFacade.create(resetPasswordToken);
        sendEmail(resetPasswordToken,
                i18n.getMessage(I18n.ACCOUNT_PASSWORD_RESET),
                i18n.getMessage(I18n.ACCOUNT_PASS_RESET_MAIL_TITLE),
                String.format("%s/resetPassword/%s", config.getFrontendURL(), resetPasswordToken.getId().toString()));
    }

    /**
     * Wysyła e-mail weryfikujący wykonywane akcje
     * @param token token weryfikujący
     * @param senderName nazwa nadawcy (nie jest to adres e-mail)
     * @param subject temat wiadomości
     * @param message treść wiadomości
     * @throws AppBaseException w przypadku niepowodzenia operacji
     */
    private void sendEmail(VerificationToken token, String senderName, String subject, String message) throws AppBaseException {
        //TODO: disable in dev? mock server maybe?
        HttpResponse<JsonNode> request = Unirest.post(config.getApiServer() + "/messages")
                .basicAuth("api", config.getMailApiKey())
                .queryString("from", String.format("%s <%s>", senderName, config.getMailSender()))
                .queryString("to", token.getAccount().getAccountDetails().getEmail())
                .queryString("subject", subject)
                .queryString("text", message)
                .asJson();

        if (request.getStatus() != 200) {
            throw VerificationTokenException.mailFailure(token);
        }
    }
}

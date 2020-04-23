package pl.lodz.p.it.ssbd2020.ssbd04.mok.services;

import kong.unirest.HttpResponse;
import kong.unirest.JsonNode;
import kong.unirest.Unirest;
import pl.lodz.p.it.ssbd2020.ssbd04.exceptions.AppBaseException;
import pl.lodz.p.it.ssbd2020.ssbd04.exceptions.VerificationTokenException;
import pl.lodz.p.it.ssbd2020.ssbd04.mok.entities.Account;
import pl.lodz.p.it.ssbd2020.ssbd04.mok.entities.verification_tokens.RegisterToken;
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
     * Tworzy nowy żeton i wysyła go na email odpowiadający kontu.
     *
     * @param account
     * @throws AppBaseException
     */
    public void sendRegisterToken(Account account) throws AppBaseException {
        RegisterToken registerToken = new RegisterToken(LocalDateTime.now().plusDays(1), account);
        verificationTokenFacade.create(registerToken);
        // TODO: disable mail sending on dev
        sendRegisterEmail(registerToken);
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
    public Account confirm(UUID tokenId) throws AppBaseException {
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

    private void sendRegisterEmail(VerificationToken token) throws AppBaseException {
        HttpResponse<JsonNode> request = Unirest.post(config.getApiServer() + "/messages")
                .basicAuth("api", config.getMailApiKey())
                .queryString("from", String.format("%s <%s>", i18n.getMessage(I18n.ACCOUNT_REGISTRATION), config.getMailSender()))
                .queryString("to", token.getAccount().getAccountDetails().getEmail())
                .queryString("subject", i18n.getMessage(I18n.ACCOUNT_REGISTER_MAIL_TITLE))
                .queryString("text", String.format("%s/confirm/%s", config.getFrontendURL(), token.getId().toString()))
                .asJson();

        if (request.getStatus() != 200) {
            throw VerificationTokenException.mailFailure(token);
        }
    }
}

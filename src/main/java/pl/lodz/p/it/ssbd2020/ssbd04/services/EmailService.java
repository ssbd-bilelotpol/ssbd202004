package pl.lodz.p.it.ssbd2020.ssbd04.services;

import kong.unirest.HttpResponse;
import kong.unirest.JsonNode;
import kong.unirest.Unirest;
import pl.lodz.p.it.ssbd2020.ssbd04.common.Config;
import pl.lodz.p.it.ssbd2020.ssbd04.exceptions.AppBaseException;
import pl.lodz.p.it.ssbd2020.ssbd04.interceptors.TrackingInterceptor;

import javax.annotation.security.PermitAll;
import javax.ejb.Asynchronous;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.enterprise.event.Event;
import javax.enterprise.event.Observes;
import javax.enterprise.event.TransactionPhase;
import javax.inject.Inject;
import javax.interceptor.Interceptors;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Serwis dostarczający funkcjonalność wysyłania wiadomości email.
 */
@Stateless
@TransactionAttribute(TransactionAttributeType.REQUIRED)
@Interceptors({TrackingInterceptor.class})
@PermitAll
public class EmailService {
    private static final Logger LOGGER = Logger.getLogger(EmailService.class.getName());

    @Inject
    private Config config;

    @Inject
    private Event<EmailEvent> emailEventProducer;

    /**
     * Metoda do użytku wewnętrznego wysyłająca emaile po zatwierdzeniu transakcji.
     * Ze względu na wymagania Java EE jest publiczna, ale nie powinna być wywoływana z kodu.
     * @param email zdarzenie emaila
     * @throws AppBaseException gdy nie uda się wysłać emaila
     */
    @Asynchronous
    public void transactionalEmailListener(
            @Observes(during = TransactionPhase.AFTER_SUCCESS) EmailEvent email) {
        try {
            sendEmail(email.getEmail(), email.getSenderName(), email.getSubject(), email.getMessage());
        } catch (AppBaseException e) {
            LOGGER.log(Level.INFO, "Failed to send an email asynchronously");
        }
    }

    /**
     * Wysyła email tylko w przypadku zatwierdzenia transakcji. Jeżeli wysyłanie maila się nie powiedzie,
     * użytkownikowi nie zostanie przekazany błąd.
     *
     * @param email      email adresata
     * @param senderName nazwa nadawcy (nie jest to adres email)
     * @param subject    temat wiadomości
     * @param message    treść wiadomości
     */
    public void sendTransactionalEmail(String email, String senderName, String subject, String message) {
        emailEventProducer.fire(new EmailEvent(email, senderName, subject, message));
    }

    /**
     * Wysyła email na podany adres
     *
     * @param email      email adresata
     * @param senderName nazwa nadawcy (nie jest to adres email)
     * @param subject    temat wiadomości
     * @param message    treść wiadomości
     * @throws AppBaseException w przypadku niepowodzenia operacji
     */
    public void sendEmail(String email, String senderName, String subject, String message) throws AppBaseException {
        HttpResponse<JsonNode> request = Unirest.post(config.getApiServer() + "/messages")
                .basicAuth("api", config.getMailApiKey())
                .queryString("from", String.format("%s <%s>", senderName, config.getMailSender()))
                .queryString("to", email)
                .queryString("subject", subject)
                .queryString("html", message)
                .asJson();

        if (request.getStatus() != 200) {
            throw AppBaseException.emailError();
        }
    }

    private static class EmailEvent {
        String email;
        String senderName;
        String subject;
        String message;

        public EmailEvent(String email, String senderName, String subject, String message) {
            this.email = email;
            this.senderName = senderName;
            this.subject = subject;
            this.message = message;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getSenderName() {
            return senderName;
        }

        public void setSenderName(String senderName) {
            this.senderName = senderName;
        }

        public String getSubject() {
            return subject;
        }

        public void setSubject(String subject) {
            this.subject = subject;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }
    }
}

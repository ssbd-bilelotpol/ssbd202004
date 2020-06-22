package pl.lodz.p.it.ssbd2020.ssbd04.security;

import org.apache.commons.codec.binary.Base64;
import pl.lodz.p.it.ssbd2020.ssbd04.common.Config;
import pl.lodz.p.it.ssbd2020.ssbd04.exceptions.AppBaseRuntimeException;

import javax.annotation.PostConstruct;
import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.io.Serializable;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

/**
 * Klasa umożliwiająca podpisywanie HMAC SHA-256
 */
@ApplicationScoped
public class MessageSigner implements Serializable {

    @Inject
    private Config config;

    private Mac hmac;

    /**
     * Metoda inicjalizująca mechanizm podpisywania HMAC SHA-256
     */
    @PostConstruct
    public void init() {
        try {
            hmac = Mac.getInstance("HmacSHA256");
            String secret = config.getEtagSecretKey();
            SecretKeySpec secretKey = new SecretKeySpec(secret.getBytes(), "HmacSHA256");
            hmac.init(secretKey);
        } catch (NoSuchAlgorithmException | InvalidKeyException e) {
            throw AppBaseRuntimeException.signingAlgorithmError(e);
        }
    }

    /**
     * Metoda kodująca ciąg znaków za pomocą Base64
     *
     * @param message wiadomość 
     * @return zakodowana wiadomość
     */
    public String sign(String message) {
        return Base64.encodeBase64String(hmac.doFinal(message.getBytes()));
    }

    /**
     * Metoda tworzoąca podpis HMAC SHA-256 obiektu
     *
     * @param signable obiekt która ma zostać podpisany
     * @return ciąg znaków zakodowany w Base64
     */
    public String sign(Signable signable) {
        String encodedMessage = Base64.encodeBase64String(signable.createMessage().getBytes());
        String signature = Base64.encodeBase64String(hmac.doFinal(encodedMessage.getBytes()));
        return encodedMessage + "." + signature;
    }

}

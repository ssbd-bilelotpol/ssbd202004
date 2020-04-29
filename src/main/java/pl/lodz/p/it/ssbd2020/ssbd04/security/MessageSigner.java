package pl.lodz.p.it.ssbd2020.ssbd04.security;

import org.apache.commons.codec.binary.Base64;
import pl.lodz.p.it.ssbd2020.ssbd04.exceptions.AppBaseRuntimeException;
import pl.lodz.p.it.ssbd2020.ssbd04.utils.Config;

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

    public String sign(String message) {
        return Base64.encodeBase64String(hmac.doFinal(message.getBytes()));
    }

    public String sign(Signable signable) {
        String encodedMessage = Base64.encodeBase64String(signable.createMessage().getBytes());
        String signature = Base64.encodeBase64String(hmac.doFinal(encodedMessage.getBytes()));
        return encodedMessage + "." + signature;
    }

}

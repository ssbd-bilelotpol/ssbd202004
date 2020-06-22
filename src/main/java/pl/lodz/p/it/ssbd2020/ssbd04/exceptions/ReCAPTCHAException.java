package pl.lodz.p.it.ssbd2020.ssbd04.exceptions;

import static pl.lodz.p.it.ssbd2020.ssbd04.common.I18n.*;

/**
 * Wyjątek odpowiadający błędom występującym podczas walidacji mechanizmu Captcha
 */
public class ReCAPTCHAException extends AppBaseException {

    private ReCAPTCHAException(String message) {
        super(message);
    }

    /**
     * Wyjątek reprezentujący podanie niewłaściwego kodu captcha.
     *
     * @return wyjątek typu ReCAPTCHAException
     */
    public static ReCAPTCHAException invalidCaptcha() {
        return new ReCAPTCHAException(CAPTCHA_INVALID);
    }

    /**
     * Wyjątek reprezentujący podanie kodu w niewłaściwym formacie
     *
     * @return wyjątek typu ReCAPTCHAException
     */
    public static ReCAPTCHAException invalidFormat() {
        return new ReCAPTCHAException(CAPTCHA_FORMAT_INVALID);
    }

    /**
     * Wyjątek reprezentujący niemożność połączenia do API ReCAPTCHA
     *
     * @return wyjątek typu ReCAPTCHAException
     */
    public static ReCAPTCHAException cantConnect() {
        return new ReCAPTCHAException(CAPTCHA_CANT_CONNECT);
    }
}

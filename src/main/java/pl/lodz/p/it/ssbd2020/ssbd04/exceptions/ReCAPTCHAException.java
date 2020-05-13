package pl.lodz.p.it.ssbd2020.ssbd04.exceptions;

import static pl.lodz.p.it.ssbd2020.ssbd04.common.I18n.*;

public class ReCAPTCHAException extends AppBaseException {

    private ReCAPTCHAException(String message) {
        super(message);
    }

    public static ReCAPTCHAException invalidCaptcha(){
        return new ReCAPTCHAException(CAPTCHA_INVALID);
    }

    public static ReCAPTCHAException invalidFormat() {
        return new ReCAPTCHAException(CAPTCHA_FORMAT_INVALID);
    }

    public static ReCAPTCHAException cantConnect() {
        return new ReCAPTCHAException(CAPTCHA_CANT_CONNECT);
    }
}

package pl.lodz.p.it.ssbd2020.ssbd04.security;


import kong.unirest.Unirest;
import pl.lodz.p.it.ssbd2020.ssbd04.common.Config;
import pl.lodz.p.it.ssbd2020.ssbd04.exceptions.AppBaseException;
import pl.lodz.p.it.ssbd2020.ssbd04.exceptions.ReCAPTCHAException;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.Context;
import java.util.logging.Logger;
import java.util.regex.Pattern;

/**
 * Zasób zajmujący się walidacją reCAPTCHA
 */
@RequestScoped
public class ReCAPTCHAService {

    private static final Logger LOGGER = Logger.getLogger(ReCAPTCHAService.class.getName());

    @Inject
    private Config config;

    @Context
    private HttpServletRequest httpServletRequest;

    private static Pattern RESPONSE_PATTERN = Pattern.compile("[A-Za-z0-9_-]+");

    /**
     * Metoda zajmująca się weryfikacją przysłanego wyniku captcha.
     * @param response captcha, przesłana od klienta.
     * @throws AppBaseException w wypadku, gdy captcha nie spełnia założeń.
     */
    public void checkCaptcha(String response) throws AppBaseException {
        if (!sanityCheck(response)) {
            throw ReCAPTCHAException.invalidFormat();
        }

        String remoteIP = httpServletRequest.getRemoteAddr();
        String URL = String.format("https://www.google.com/recaptcha/api/siteverify?secret=%s&response=%s&remoteip=%s",
                config.getGoogleSecret(), response, remoteIP);

        ReCAPTCHA googleCaptcha = Unirest.get(URL).asObject(ReCAPTCHA.class).getBody();

        if (!googleCaptcha.isSuccess() || googleCaptcha.getScore() < config.getGoogleThreshold()) {
            throw ReCAPTCHAException.invalidCaptcha();
        }
    }


    private boolean sanityCheck(String response) {
        return response.length() != 0 && RESPONSE_PATTERN.matcher(response).matches();
    }
}

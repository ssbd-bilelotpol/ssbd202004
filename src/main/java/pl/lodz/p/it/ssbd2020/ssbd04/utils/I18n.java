package pl.lodz.p.it.ssbd2020.ssbd04.utils;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import java.util.ResourceBundle;

/**
 * Odpowiada za internacjonalizacje komunikatów.
 * Definiuje ciągi znakowe, które są używane przez aplikację.
 */
@RequestScoped
public class I18n {
    public static final String REST_VALIDATION_ERROR = "error.rest.validation";
    public static final String DATABASE_OPTIMISTIC_LOCK = "error.database.optimisticLock";
    public static final String DATABASE_OPERATION = "error.database.operation";
    public static final String ACCOUNT_LOGIN_EXISTS = "error.account.loginExists";
    public static final String ACCOUNT_EMAIL_EXISTS = "error.account.emailExists";
    public static final String ACCOUNT_REGISTER_INVALID_TOKEN = "error.account.register.invalidToken";
    public static final String ACCOUNT_REGISTER_ALREADY_CONFIRMED = "error.account.register.alreadyConfirmed";
    public static final String ACCOUNT_REGISTER_MAIL_FAILURE = "error.account.register.mailFailure";
    public static final String ACCOUNT_REGISTER_MAIL_TITLE = "account.register.mail.title";
    public static final String ACCOUNT_REGISTRATION = "account.registration";

    @Inject
    HttpServletRequest request;

    private ResourceBundle bundle;

    @PostConstruct
    private void init() {
        bundle = ResourceBundle.getBundle("messages", request.getLocale());
    }

    public String getMessage(String key) {
        return bundle.getString(key);
    }
}

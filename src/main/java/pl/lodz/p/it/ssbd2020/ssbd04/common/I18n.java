package pl.lodz.p.it.ssbd2020.ssbd04.common;

import javax.annotation.PostConstruct;
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
    public static final String ACTION_FAILED = "error.rest.actionFailed";
    public static final String MESSAGE_SIGNER_OPERATION = "error.messageSigner.operation";
    public static final String ETAG_WRONG_VALUE = "error.request.etagWrongValue";
    public static final String AUTH_INCORRECT_LOGIN_OR_PASSWORD = "error.auth.incorrectLoginOrPassword";
    public static final String ACCOUNT_LOGIN_EXISTS = "error.account.loginExists";
    public static final String ACCOUNT_NOT_FOUND = "error.account.notFound";
    public static final String ACCOUNT_ACCESS_LEVEL_NOT_FOUND = "error.accountAccessLevel.notFound";
    public static final String ACCOUNT_ACCESS_LEVEL_ALREADY_ASSIGNED = "error.accountAccessLevel.alreadyAssigned";
    public static final String ACCOUNT_ACCESS_LEVEL_EXISTS = "error.account.accountAccessLevelExists";
    public static final String ACCOUNT_EMAIL_EXISTS = "error.account.emailExists";
    public static final String ACCOUNT_REGISTER_INVALID_TOKEN = "error.account.register.invalidToken";
    public static final String ACCOUNT_REGISTER_ALREADY_CONFIRMED = "error.account.register.alreadyConfirmed";
    public static final String ACCOUNT_NOT_ACTIVE = "error.account.notActive";
    public static final String ACCOUNT_NOT_CONFIRMED = "error.account.notConfirmed";
    public static final String ACCOUNT_PASSWORDS_DONT_MATCH = "error.account.passwordsDontMatch";
    public static final String ACCOUNT_PASSWORD_IS_THE_SAME = "error.account.passwordIsTheSame";
    public static final String TOKEN_EXPIRED = "error.token.expired";
    public static final String TOKEN_MAIL_FAILURE = "error.token.mailFailure";
    public static final String ACCOUNT_BLOCKED = "error.auth.accountBlocked";
    public static final String EMAIL_FAILURE = "error.mail.failure";
    public static final String CAPTCHA_INVALID = "error.captcha.invalid";
    public static final String CAPTCHA_FORMAT_INVALID = "error.captcha.format.invalid";
    public static final String CAPTCHA_CANT_CONNECT = "error.captcha.cantConnect";
    public static final String AIRPORT_CODE_NOT_UNIQUE = "error.airport.codeNotUnique";
    public static final String AIRPORT_NOT_FOUND = "error.airport.notFound";
    public static final String AIRPORT_IN_USE = "error.airport.inUse";
    public static final String CONNECTION_EXISTS = "error.connection.exists";
    public static final String CONNECTION_TARGET_NOT_FOUND = "error.connection.targetNotFound";
    public static final String CONNECTION_SOURCE_NOT_FOUND = "error.connection.sourceNotFound";
    public static final String CONNECTION_IN_USE = "error.connection.inUse";
    public static final String FLIGHT_EXISTS = "error.flight.exists";

    public static final String ACCOUNT_REGISTER_MAIL_TITLE = "account.register.mail.title";
    public static final String ACCOUNT_REGISTER_MAIL_CONTENT = "account.register.mail.content";
    public static final String ACCOUNT_REGISTRATION_MAIL_SENDER = "account.registration";
    public static final String ACCOUNT_PASS_RESET_MAIL_TITLE = "account.passwordReset.mail.title";
    public static final String ACCOUNT_PASS_RESET_MAIL_CONTENT = "account.passwordReset.mail.content";
    public static final String ACCOUNT_PASSWORD_RESET_MAIL_SENDER = "account.passwordReset";
    public static final String ACCOUNT_BLOCKED_MAIL_SENDER = "account.block";
    public static final String ACCOUNT_BLOCKED_MAIL_TITLE = "account.block.mail.title";
    public static final String ACCOUNT_BLOCKED_MAIL_CONTENT = "account.block.mail.content";
    public static final String ACCOUNT_UNBLOCKED_MAIL_SENDER = "account.unblock";
    public static final String ACCOUNT_UNBLOCKED_MAIL_TITLE = "account.unblock.mail.title";
    public static final String ACCOUNT_UNBLOCKED_MAIL_CONTENT = "account.unblock.mail.content";
    public static final String ACCOUNT_PASSWORD_RESET = "account.passwordReset";
    public static final String ACCOUNT_ADMIN_LOGIN_TITLE = "account.adminLogin.title";
    public static final String ACCOUNT_ADMIN_LOGIN_SENDER = "account.adminLogin.sender";
    public static final String ACCOUNT_ADMIN_LOGIN_CONTENT = "account.adminLogin.content";

    public static final String PROCESSING_ERROR = "error.rest.processingError";
    public static final String UNAUTHORIZED = "error.rest.unauthorized";

    public static final String AIRPLANE_SCHEMA_NOT_FOUND = "error.airplaneSchema.notFound";
    public static final String AIRPLANE_SCHEMA_IN_USE = "error.airplaneSchema.inUse";

    public static final String SEAT_CLASS_NOT_FOUND = "error.seatClass.notFound";
    public static final String SEAT_CLASS_IN_USE = "error.seatClass.inUse";
    public static final String SEAT_CLASS_NAME_TAKEN = "error.seatClass.nameTaken";
    public static final String SEAT_CLASS_BENEFIT_EXISTS = "error.seatClass.benefitExists";
    public static final String MAIL_FOOTER = "mail.footer";


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

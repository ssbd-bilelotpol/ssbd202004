package pl.lodz.p.it.ssbd2020.ssbd04.exceptions;

import pl.lodz.p.it.ssbd2020.ssbd04.entities.Account;

import static pl.lodz.p.it.ssbd2020.ssbd04.common.I18n.*;

/**
 * Wyjątek odpowiadający hierarchii klas Account.
 */
public class AccountException extends AppBaseException {
    private final Account account;

    private AccountException(String message, Throwable cause, Account account) {
        super(message, cause);
        this.account = account;
    }

    private AccountException(String message, Account account) {
        super(message);
        this.account = account;
    }

    private AccountException(String message, Throwable cause) {
        super(message, cause);
        this.account = null;
    }

    public static AccountException loginExists(Throwable cause, Account account) {
        return new AccountException(ACCOUNT_LOGIN_EXISTS, cause, account);
    }

    public static AccountException emailExists(Throwable cause, Account account) {
        return new AccountException(ACCOUNT_EMAIL_EXISTS, cause, account);
    }

    public static AccountException accountAccessLevelExists(Throwable cause, Account account) {
        return new AccountException(ACCOUNT_ACCESS_LEVEL_EXISTS, cause, account);
    }

    public static AccountException noExists(Throwable cause) {
        return new AccountException(ACCOUNT_NOT_FOUND, cause);
    }

    public static AccountException notActive(Account account) {
        return new AccountException(ACCOUNT_NOT_ACTIVE, account);
    }

    public static AccountException notConfirmed(Account account) {
        return new AccountException(ACCOUNT_NOT_CONFIRMED, account);
    }

    public static AccountException passwordsDontMatch(Account account) {
        return new AccountException(ACCOUNT_PASSWORDS_DONT_MATCH, account);
    }
}

package pl.lodz.p.it.ssbd2020.ssbd04.exceptions;

import pl.lodz.p.it.ssbd2020.ssbd04.mok.entities.Account;

import java.util.UUID;

import static pl.lodz.p.it.ssbd2020.ssbd04.utils.I18n.ACCOUNT_EMAIL_EXISTS;
import static pl.lodz.p.it.ssbd2020.ssbd04.utils.I18n.ACCOUNT_LOGIN_EXISTS;

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

    public static AccountException loginExists(Throwable cause, Account account) {
        return new AccountException(ACCOUNT_LOGIN_EXISTS, cause, account);
    }

    public static AccountException emailExists(Throwable cause, Account account) {
        return new AccountException(ACCOUNT_EMAIL_EXISTS, cause, account);
    }
}

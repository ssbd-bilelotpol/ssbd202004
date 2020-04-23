package pl.lodz.p.it.ssbd2020.ssbd04.exceptions;

import static pl.lodz.p.it.ssbd2020.ssbd04.utils.I18n.*;

/**
 * Wyjątek odpowiadający hierarchii klas AccountAccessLevel.
 */
public class AccountAccessLevelException extends AppBaseException {

    private AccountAccessLevelException(String message, Throwable cause) {
        super(message, cause);
    }

    private AccountAccessLevelException(String message) {
        super(message);
    }

    public static AccountAccessLevelException notFound() {
        return new AccountAccessLevelException(ACCOUNT_ACCESS_LEVEL_NOT_FOUND);
    }

    public static AccountAccessLevelException alreadyAssigned(Throwable cause) {
        return new AccountAccessLevelException(ACCOUNT_ACCESS_LEVEL_ALREADY_ASSIGNED, cause);
    }

}

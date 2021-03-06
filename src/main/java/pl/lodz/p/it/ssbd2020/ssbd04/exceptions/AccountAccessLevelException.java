package pl.lodz.p.it.ssbd2020.ssbd04.exceptions;

import static pl.lodz.p.it.ssbd2020.ssbd04.common.I18n.ACCOUNT_ACCESS_LEVEL_ALREADY_ASSIGNED;
import static pl.lodz.p.it.ssbd2020.ssbd04.common.I18n.ACCOUNT_ACCESS_LEVEL_NOT_FOUND;

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

    /**
     * Tworzy wyjątek reprezentujący brak poziomu dostępu
     *
     * @return wyjątek AccountAccessLevelException
     */
    public static AccountAccessLevelException notFound() {
        return new AccountAccessLevelException(ACCOUNT_ACCESS_LEVEL_NOT_FOUND);
    }

    /**
     * Tworzy wyjątek reprezentujący próbę przypisania już istniejącego poziomu dostępu
     *
     * @param cause wyjątek powodujący stworzenie wyjątku
     * @return wyjątek AccountAccessLevelException
     */
    public static AccountAccessLevelException alreadyAssigned(Throwable cause) {
        return new AccountAccessLevelException(ACCOUNT_ACCESS_LEVEL_ALREADY_ASSIGNED, cause);
    }

}

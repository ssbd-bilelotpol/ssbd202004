package pl.lodz.p.it.ssbd2020.ssbd04.exceptions;

import pl.lodz.p.it.ssbd2020.ssbd04.entities.Account;

import static pl.lodz.p.it.ssbd2020.ssbd04.common.I18n.*;

/**
 * Wyjątek odpowiadający hierarchii klas Account.
 */
public class AccountException extends AppBaseException {
    private final Account account;

    private AccountException(String message) {
        super(message);
        this.account = null;
    }

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

    /**
     * Wyjątek reprezentujący błąd podczas edycji/tworzenia konta,
     * który mówi, że login jest już zajęty
     *
     * @param cause   wyjątek powodujący błąd
     * @param account konto, które podlegało modyfikacji/utworzeniu
     * @return wyjątek typu AccountException
     */
    public static AccountException loginExists(Throwable cause, Account account) {
        return new AccountException(ACCOUNT_LOGIN_EXISTS, cause, account);
    }

    /**
     * Wyjątek reprezentujący błąd podczas edycji/tworzenia konta,
     * który mówi, że email jest już zajęty
     *
     * @param cause   wyjątek powodujący błąd
     * @param account konto, które podlegało modyfikacji/utworzeniu
     * @return wyjątek typu AccountException
     */
    public static AccountException emailExists(Throwable cause, Account account) {
        return new AccountException(ACCOUNT_EMAIL_EXISTS, cause, account);
    }

    /**
     * Wyjątek reprezentujący, próbę wykonania operacji na zablokowanym koncie
     *
     * @return wyjątek typu AccountException
     */
    public static AccountException accountBlocked() {
        return new AccountException(ACCOUNT_BLOCKED);
    }

    /**
     * Wyjątek reprezentujący próbę wykonania operacji na nieistniejącym koncie
     *
     * @param cause wyjątek powodujący błąd
     * @return wyjątek typu AccountException
     */
    public static AccountException noExists(Throwable cause) {
        return new AccountException(ACCOUNT_NOT_FOUND, cause);
    }

    /**
     * Wyjątek reprezentujący próbę wykonania operacji na nieaktywnym koncie
     *
     * @param account konto, na którym próbowano dokonać operacji
     * @return wyjątek typu AccountException
     */
    public static AccountException notActive(Account account) {
        return new AccountException(ACCOUNT_NOT_ACTIVE, account);
    }

    /**
     * Wyjątek reprezentujący próbę wykonania operacji na niepotwierdzonym koncie
     *
     * @param account wyjątek powodujący błąd
     * @return wyjątek typu AccountException
     */
    public static AccountException notConfirmed(Account account) {
        return new AccountException(ACCOUNT_NOT_CONFIRMED, account);
    }

    /**
     * Wyjątek reprezentujące próbę zmiany hasła przy niepasujących hasłach
     *
     * @param account konto, na którym próbowano dokonać operacji
     * @return wyjątek typu AccountException
     */
    public static AccountException passwordsDontMatch(Account account) {
        return new AccountException(ACCOUNT_PASSWORDS_DONT_MATCH, account);
    }

    /**
     * Wyjątek reprezentujące próbę zmiany hasła na takie samo jak poprzednie
     *
     * @param account konto, na którym próbowano dokonać operacji
     * @return wyjątek typu AccountException
     */
    public static AppBaseException passwordIsTheSame(Account account) {
        return new AccountException(ACCOUNT_PASSWORD_IS_THE_SAME, account);
    }
}

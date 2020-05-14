package pl.lodz.p.it.ssbd2020.ssbd04.exceptions;

import javax.ejb.ApplicationException;
import javax.persistence.OptimisticLockException;
import javax.persistence.PersistenceException;

import static pl.lodz.p.it.ssbd2020.ssbd04.common.I18n.*;

/**
 * Generyczny wyjątek aplikacyjny, po którego wystąpieniu transakcje aplikacyjne są odwoływane.
 */
@ApplicationException(rollback = true)
public class AppBaseException extends Exception {
    protected AppBaseException(String message, Throwable cause) {
        super(message, cause);
    }

    protected AppBaseException(String message) {
        super(message);
    }

    public static AppBaseException optimisticLock() {
        return new AppBaseException(DATABASE_OPTIMISTIC_LOCK);
    }

    public static AppBaseException optimisticLock(OptimisticLockException e) {
        return new AppBaseException(DATABASE_OPTIMISTIC_LOCK, e);
    }

    public static AppBaseException databaseOperation(PersistenceException e) {
        return new AppBaseException(DATABASE_OPERATION, e);
    }

    public static AppBaseException actionFailed() {
        return new AppBaseException(ACTION_FAILED);
    }

    public static AppBaseException emailError() {
        return new AppBaseException(EMAIL_FAILURE);
    }
}

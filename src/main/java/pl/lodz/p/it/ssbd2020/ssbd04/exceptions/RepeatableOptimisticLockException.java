package pl.lodz.p.it.ssbd2020.ssbd04.exceptions;

import pl.lodz.p.it.ssbd2020.ssbd04.common.I18n;

public class RepeatableOptimisticLockException extends AppBaseException {

    protected RepeatableOptimisticLockException(String message) {
        super(message);
    }

    /**
     * Wyjątek aplikacyjny pozwalający na powtórzenie transakcji na wypadek niezgodności wersji.
     * @return
     */
    public static RepeatableOptimisticLockException optimisticLock() {
        return new RepeatableOptimisticLockException(I18n.DATABASE_OPTIMISTIC_LOCK);
    }
}

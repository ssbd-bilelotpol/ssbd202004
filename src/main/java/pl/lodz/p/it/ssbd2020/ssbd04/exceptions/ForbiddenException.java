package pl.lodz.p.it.ssbd2020.ssbd04.exceptions;

import static pl.lodz.p.it.ssbd2020.ssbd04.common.I18n.FORBIDDEN;

/**
 * Reprezentuje wyjątek zabraniający wykonanie danej metody biznesowej
 */
public class ForbiddenException extends AppBaseException {
    private ForbiddenException(String message) {
        super(message);
    }

    /**
     * Tworzy wyjątek reprezentujący brak dostępu do metody (logika biznesowa)
     * @return ForbiddenException
     */
    public static ForbiddenException forbidden() {
        return new ForbiddenException(FORBIDDEN);
    }
}

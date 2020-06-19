package pl.lodz.p.it.ssbd2020.ssbd04.exceptions;

import static pl.lodz.p.it.ssbd2020.ssbd04.common.I18n.FORBIDDEN;

public class ForbiddenException extends AppBaseException {
    private ForbiddenException(String message) {
        super(message);
    }

    public static ForbiddenException forbidden() {
        return new ForbiddenException(FORBIDDEN);
    }
}

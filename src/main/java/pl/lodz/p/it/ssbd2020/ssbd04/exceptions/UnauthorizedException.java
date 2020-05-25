package pl.lodz.p.it.ssbd2020.ssbd04.exceptions;

import static pl.lodz.p.it.ssbd2020.ssbd04.common.I18n.UNAUTHORIZED;

public class UnauthorizedException extends AppBaseException {
    private UnauthorizedException(String message, Throwable cause) {
        super(message, cause);
    }

    public static UnauthorizedException unauthorized(Throwable cause) {
        return new UnauthorizedException(UNAUTHORIZED, cause);
    }
}

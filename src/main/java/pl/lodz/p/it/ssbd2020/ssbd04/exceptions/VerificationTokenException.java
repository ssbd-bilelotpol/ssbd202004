package pl.lodz.p.it.ssbd2020.ssbd04.exceptions;

import pl.lodz.p.it.ssbd2020.ssbd04.entities.verification_tokens.RegisterToken;
import pl.lodz.p.it.ssbd2020.ssbd04.entities.verification_tokens.VerificationToken;

import java.util.UUID;

import static pl.lodz.p.it.ssbd2020.ssbd04.common.I18n.*;

/**
 * Wyjątek odpowiadający hierarchii klas VerificationToken.
 */
public class VerificationTokenException extends AppBaseException {
    private final VerificationToken token;

    private VerificationTokenException(String message, Throwable cause, VerificationToken token) {
        super(message, cause);
        this.token = token;
    }

    private VerificationTokenException(String message, VerificationToken token) {
        super(message);
        this.token = token;
    }

    /**
     * Wyjątek reprezentujący próbę wykonania operacji na niewłaściwym tokenie
     * @param token identyfikator tokenu
     * @return wyjątek typu VerificationTicketException
     */
    public static VerificationTokenException invalidRegisterToken(UUID token) {
        return new VerificationTokenException(ACCOUNT_REGISTER_INVALID_TOKEN, new RegisterToken(token));
    }

    /**
     * Wyjątek reprezentujący próbę wykonania operacji na niewłaściwym tokenie
     * @param verificationToken przyczyna problemu
     * @return wyjątek typu VerificationTicketException
     */
    public static VerificationTokenException invalidRegisterToken(VerificationToken verificationToken) {
        return new VerificationTokenException(ACCOUNT_REGISTER_INVALID_TOKEN, verificationToken);
    }

    /**
     * Wyjątek reprezentujący próbę potwierdzenia już potwierdzonego konta
     * @param verificationToken przyczyna problemu
     * @return wyjątek typu VerificationTicketException
     */
    public static VerificationTokenException accountAlreadyConfirmed(VerificationToken verificationToken) {
        return new VerificationTokenException(ACCOUNT_REGISTER_ALREADY_CONFIRMED, verificationToken);
    }

    /**
     * Wyjątek reprezentujący niemożność wysłania maila podczas rejestracji
     * @param verificationToken przyczyna problemu
     * @return wyjątek typu VerificationTicketException
     */
    public static VerificationTokenException mailFailure(VerificationToken verificationToken) {
        return new VerificationTokenException(TOKEN_MAIL_FAILURE, verificationToken);
    }

    /**
     * Wyjątek reprezentujący próbę wykorzystania wygasłego tokenu
     * @param verificationToken przyczyna problemu
     * @return wyjątek typu VerificationTicketException
     */
    public static VerificationTokenException expired(VerificationToken verificationToken) {
        return new VerificationTokenException(TOKEN_EXPIRED, verificationToken);
    }
}

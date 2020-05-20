package pl.lodz.p.it.ssbd2020.ssbd04.entities.verification_tokens;

import pl.lodz.p.it.ssbd2020.ssbd04.entities.Account;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.UUID;

/**
 * Używany do resetowania hasła
 */
@Entity
@DiscriminatorValue("reset")
public class ResetPasswordToken extends VerificationToken implements Serializable {
    public ResetPasswordToken() {
        super();
    }

    public ResetPasswordToken(LocalDateTime expireDateTime, Account account) {
        super(expireDateTime, account);
    }

    public ResetPasswordToken(UUID id) {
        super(id);
    }

    @Override
    public String toString() {
        return "ResetPasswordToken{" + super.toString() + "}";
    }
}

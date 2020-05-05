package pl.lodz.p.it.ssbd2020.ssbd04.entities.verification_tokens;

import pl.lodz.p.it.ssbd2020.ssbd04.entities.Account;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.UUID;

/**
 * Używany do autoryzacji konta po rejestracji.
 */
@Entity
@DiscriminatorValue("register")
public class RegisterToken extends VerificationToken implements Serializable {
    public RegisterToken() {
    }

    public RegisterToken(LocalDateTime expireDateTime, Account account) {
        super(expireDateTime, account);
    }

    public RegisterToken(UUID id) {
        super(id);
    }

    @Override
    public String toString() {
        return "RegisterToken{} " + super.toString();
    }
}

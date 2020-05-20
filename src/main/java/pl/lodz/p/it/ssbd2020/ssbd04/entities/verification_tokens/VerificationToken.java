package pl.lodz.p.it.ssbd2020.ssbd04.entities.verification_tokens;

import pl.lodz.p.it.ssbd2020.ssbd04.common.AbstractEntity;
import pl.lodz.p.it.ssbd2020.ssbd04.entities.Account;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

/**
 * Reprezentuje abstrakcyjny żeton służący do potwierdzenia wykonania operacji, np. rejestracja konta, reset hasła.
 */
@Entity
@Table(
        name = "verification_token",
        indexes = {
                @Index(name = "verification_token_account_fk", columnList = "account_id")
        }
)
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "type", discriminatorType = DiscriminatorType.STRING)
@NamedQueries({
        @NamedQuery(name = "VerificationToken.findExpiredBefore", query = "SELECT t FROM VerificationToken t WHERE expireDateTime < :dateTime")
})
public abstract class VerificationToken extends AbstractEntity implements Serializable {
    @Id
    @Column(updatable = false)
    private String id;

    @NotNull
    @Column(nullable = false, name = "expire_date_time", updatable = false)
    private LocalDateTime expireDateTime;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "account_id", nullable = false, updatable = false, foreignKey = @ForeignKey(name = "verification_token_account_fk"))
    private Account account;

    public VerificationToken() {
        this.id = UUID.randomUUID().toString();
    }

    public VerificationToken(LocalDateTime expireDateTime, Account account) {
        this.id = UUID.randomUUID().toString();
        this.expireDateTime = expireDateTime;
        this.account = account;
    }

    public VerificationToken(UUID id) {
        this.id = id.toString();
    }

    public UUID getId() {
        return UUID.fromString(id);
    }

    public LocalDateTime getExpireDateTime() {
        return expireDateTime;
    }

    public Account getAccount() {
        return account;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof VerificationToken)) return false;
        VerificationToken that = (VerificationToken) o;
        return expireDateTime.equals(that.expireDateTime) &&
                account.equals(that.account);
    }

    @Override
    public int hashCode() {
        return Objects.hash(expireDateTime, account);
    }

    @Override
    public String toString() {
        return "id=" + id +
                ", version=" + getVersion() +
                ", expireDateTime=" + expireDateTime +
                ", account=" + account;
    }
}

package pl.lodz.p.it.ssbd2020.ssbd04.entities.access_levels;


import pl.lodz.p.it.ssbd2020.ssbd04.common.AbstractEntity;

import javax.persistence.*;
import java.io.Serializable;

import static pl.lodz.p.it.ssbd2020.ssbd04.entities.access_levels.AccountAccessLevel.CONSTRAINT_UNIQUE_ACCOUNT_ACCESS_LEVEL;

/**
 * Klasa encyjna zawierająca informacje o poziomie dostępu konta
 */
@Entity
@Table(
        name = "account_access_level",
        indexes = {@Index(name = "account_account_access_level_fk", columnList = "account_id")},
        uniqueConstraints = {
                @UniqueConstraint(name = CONSTRAINT_UNIQUE_ACCOUNT_ACCESS_LEVEL, columnNames = {"account_id", "access_level"})
        }
)
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "access_level", discriminatorType = DiscriminatorType.STRING)
public abstract class AccountAccessLevel extends AbstractEntity implements Serializable {
    public static final String CONSTRAINT_UNIQUE_ACCOUNT_ACCESS_LEVEL = "account_account_details_id_unique";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(updatable = false)
    private Long id;

    public AccountAccessLevel() {
    }

    public Long getId() {
        return id;
    }

    public String getType() {
        return "";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        AccountAccessLevel that = (AccountAccessLevel) o;

        return getType().equals(that.getType());
    }

    @Override
    public int hashCode() {
        return getType().hashCode();
    }
}

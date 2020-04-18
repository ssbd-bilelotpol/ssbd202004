package pl.lodz.p.it.ssbd2020.ssbd04.mok.entities.access_levels;


import pl.lodz.p.it.ssbd2020.ssbd04.utils.AbstractEntity;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Klasa encyjna zawierająca informacje o poziomie dostępu konta
 */
@Entity
@Table(
        name = "account_access_level",
        indexes = {@Index(name = "account_account_access_level_fk", columnList = "account_id")},
        uniqueConstraints = {
                @UniqueConstraint(name = "account_access_level_unique", columnNames = {"account_id", "access_level"})
        }
)
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "access_level", discriminatorType = DiscriminatorType.STRING)
public abstract class AccountAccessLevel extends AbstractEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(updatable = false)
    private Long id;

    public AccountAccessLevel() {
    }

    public Long getId() {
        return id;
    }
}

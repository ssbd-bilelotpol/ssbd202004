package pl.lodz.p.it.ssbd2020.ssbd04.mok.entities.access_levels;


import javax.persistence.*;
import java.io.Serializable;

/**
 * Klasa encyjna zawierająca informacje o poziomie dostępu konta
 */
@Entity
@Table(name = "account_access_level")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "access_level", discriminatorType = DiscriminatorType.STRING)
public abstract class AccountAccessLevel implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(updatable = false)
    private Long id;

    @Version
    private Long version;

    public AccountAccessLevel() {
    }

    public Long getId() {
        return id;
    }
}

package pl.lodz.p.it.ssbd2020.ssbd04.mok.entities.access_levels;


import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.Objects;

/**
 * Klasa encyjna zawierająca informacje o poziomie dostępu konta
 */
@Entity
@Table(name = "account_access_level")
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn(name = "AccessLevel", discriminatorType = DiscriminatorType.STRING)
public abstract class AccountAccessLevel implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(updatable = false)
    private Long id;

    @NotNull
    @Column(updatable = false, nullable = false, unique = true)
    @Size(min = 1, max = 4)
    private Integer priority;

    @Version
    private Long version;

    public AccountAccessLevel() {
    }

    public AccountAccessLevel(Integer priority, Long version) {
        this.priority = priority;
        this.version = version;
    }

    public Long getId() {
        return id;
    }

    public Integer getPriority() {
        return priority;
    }

    public void setPriority(Integer name) {
        this.priority = name;
    }

    public Long getVersion() {
        return version;
    }

    public void setVersion(Long version) {
        this.version = version;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof AccountAccessLevel)) return false;

        AccountAccessLevel that = (AccountAccessLevel) o;

        return Objects.equals(priority, that.priority);
    }

    @Override
    public int hashCode() {
        return priority != null ? priority.hashCode() : 0;
    }
}

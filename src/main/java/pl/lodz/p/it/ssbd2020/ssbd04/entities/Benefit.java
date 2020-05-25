package pl.lodz.p.it.ssbd2020.ssbd04.entities;


import pl.lodz.p.it.ssbd2020.ssbd04.common.AbstractEntity;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.Objects;

import static pl.lodz.p.it.ssbd2020.ssbd04.entities.Benefit.CONSTRAINT_NAME;

/**
 * Informacje o możliwych do wykupienia benefitach
 */
@Entity
@Table(
        uniqueConstraints = {
                @UniqueConstraint(columnNames = "name", name = CONSTRAINT_NAME),
        }
)
public class Benefit extends AbstractEntity implements Serializable {
    public static final String CONSTRAINT_NAME = "benefit_name_unique";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(updatable = false)
    private Long id;

    @NotNull
    @Column(nullable = false, length = 128)
    @Size(min = 1, max = 128)
    private String name;

    @NotNull
    @Column(nullable = false)
    @Size(min = 5, max = 255)
    private String description;

    public Benefit() {
    }

    public Benefit(@NotNull @Size(min = 1, max = 128) String name, @NotNull @Size(min = 5, max = 255) String description) {
        this.name = name;
        this.description = description;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Benefit)) return false;
        Benefit benefit = (Benefit) o;
        return name.equals(benefit.name) &&
                description.equals(benefit.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, description);
    }

    @Override
    public String toString() {
        return "Benefit{" +
                "id=" + id +
                ", version=" + getVersion() +
                "}";
    }
}

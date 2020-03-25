package pl.lodz.p.it.ssbd2020.ssbd04.mob.entities;


import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.Objects;

/**
 * Informacje o możliwych do wykupienia benefitach
 */
@Entity
public class Benefit implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(updatable = false)
    private Long id;

    @NotNull
    @Column(nullable = false)
    @Size(min = 1, max = 128)
    private String name;

    @NotNull
    @Column(nullable = false)
    @Size(min = 5, max = 255)
    private String description;

    @Version
    private Long version;

    public Benefit() {
    }

    public Benefit(String name, String description, Long version) {
        this.name = name;
        this.description = description;
        this.version = version;
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

    public Long getVersion() {
        return version;
    }

    public void setVersion(Long version) {
        this.version = version;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Benefit)) return false;
        Benefit benefit = (Benefit) o;
        return Objects.equals(name, benefit.name) &&
                Objects.equals(description, benefit.description) &&
                Objects.equals(version, benefit.version);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, description, version);
    }
}

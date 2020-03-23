package pl.lodz.p.it.ssbd2020.ssbd04.mol.entities;


import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * Informacje o możliwych do wykupienia benefitach
 */

@Entity
@Table(name = "Benefit")
public class Benefit implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false, nullable = false)
    private Long id;
    private String name;
    private String description;

    @Version
    private Long version;

    public Benefit() {}

    public Benefit(String name, String description, Long version) {
        this.name = name;
        this.description = description;
        this.version = version;
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

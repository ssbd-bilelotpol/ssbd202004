package pl.lodz.p.it.ssbd2020.ssbd04.mol.entities;


import javax.persistence.*;
import java.io.Serializable;
import java.math.BigInteger;

/**
 * Informacje o możliwych do wykupienia benefitach
 */

@Entity
@Table(name = "Benefit")
public class Benefit implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id", updatable = false, nullable = false)
    private Long id;
    private String name;
    private String description;

    private BigInteger version;

    public Benefit() {}

    public Benefit(String name, String description, BigInteger version) {
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

    public BigInteger getVersion() {
        return version;
    }

    public void setVersion(BigInteger version) {
        this.version = version;
    }
}

package pl.lodz.p.it.ssbd2020.ssbd04.mol.entities;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.List;

/**
 * Informacje o klasie siedzenia i związanymi z nią benefitami
 */

@Entity
@Table(name = "SeatClass")
public abstract class SeatClass implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false, nullable = false)
    private Long id;

    @Column(nullable = false)
    private Float price;

    @OneToMany(targetEntity = Benefit.class)
    @Column(nullable = false)
    private List<Benefit> listOfBenefits;

    @Version
    @Column(nullable = false)
    private Long version;

    public SeatClass() {}

    public SeatClass(Float price, List<Benefit> listOfBenefits, Long version) {
        this.price = price;
        this.listOfBenefits = listOfBenefits;
        this.version = version;
    }

    public Long getId() {
        return id;
    }

    public Float getPrice() {
        return price;
    }

    public void setPrice(Float price) {
        this.price = price;
    }

    public List<Benefit> getListOfBenefits() {
        return listOfBenefits;
    }

    public void setListOfBenefits(List<Benefit> listOfBenefits) {
        this.listOfBenefits = listOfBenefits;
    }

    public Long getVersion() {
        return version;
    }

    public void setVersion(Long version) {
        this.version = version;
    }

}

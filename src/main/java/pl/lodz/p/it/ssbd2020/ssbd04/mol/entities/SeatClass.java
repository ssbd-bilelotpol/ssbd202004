package pl.lodz.p.it.ssbd2020.ssbd04.mol.entities;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigInteger;
import java.util.List;

/**
 * Informacje o klasie siedzenia i związanymi z nią benefitami
 */

@Entity
@Table(name = "SeatClass")
public abstract class SeatClass implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id", updatable = false, nullable = false)
    private long id;
    private float price;

    @OneToMany(targetEntity = Benefit.class)
    private List<Benefit> listOfBenefits;

    private BigInteger version;

    public SeatClass() {}

    public SeatClass(float price, List<Benefit> listOfBenefits, BigInteger version) {
        this.price = price;
        this.listOfBenefits = listOfBenefits;
        this.version = version;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public List<Benefit> getListOfBenefits() {
        return listOfBenefits;
    }

    public void setListOfBenefits(List<Benefit> listOfBenefits) {
        this.listOfBenefits = listOfBenefits;
    }

    public BigInteger getVersion() {
        return version;
    }

    public void setVersion(BigInteger version) {
        this.version = version;
    }
}

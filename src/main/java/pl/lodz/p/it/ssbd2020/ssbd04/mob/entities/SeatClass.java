package pl.lodz.p.it.ssbd2020.ssbd04.mob.entities;

import javax.persistence.*;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;
import java.util.Set;

/**
 * Informacje o klasie siedzenia i związanymi z nią benefitami
 */
@Entity
@Table(name = "seat_class")
public class SeatClass implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(updatable = false)
    private Long id;

    @NotNull
    @Size(min = 2, max = 30)
    @Column(nullable = false, unique = true, length = 30)
    private String name;

    @Digits(integer = 7, fraction = 2)
    @NotNull
    @Column(precision = 7, scale = 2, nullable = false)
    private BigDecimal price;

    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(
            name = "seat_class_benefits",
            joinColumns = @JoinColumn(name = "seat_class_id"),
            inverseJoinColumns = @JoinColumn(name = "benefit_id"),
            uniqueConstraints = @UniqueConstraint(
                    columnNames = {"seat_class_id", "benefit_id"}
            )
    )
    private Set<Benefit> listOfBenefits;

    @Version
    private Long version;

    public SeatClass() {
    }

    public SeatClass(@NotNull @Size(min = 2, max = 30) String name, @Digits(integer = 7, fraction = 2) @NotNull BigDecimal price, Set<Benefit> listOfBenefits) {
        this.name = name;
        this.price = price;
        this.listOfBenefits = listOfBenefits;
    }

    public long getId() {
        return id;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Set<Benefit> getListOfBenefits() {
        return listOfBenefits;
    }

    public void setListOfBenefits(Set<Benefit> listOfBenefits) {
        this.listOfBenefits = listOfBenefits;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof SeatClass)) return false;

        SeatClass seatClass = (SeatClass) o;

        return Objects.equals(name, seatClass.name);
    }

    @Override
    public int hashCode() {
        return name != null ? name.hashCode() : 0;
    }
}

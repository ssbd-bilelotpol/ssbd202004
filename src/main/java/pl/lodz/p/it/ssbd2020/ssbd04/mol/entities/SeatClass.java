package pl.lodz.p.it.ssbd2020.ssbd04.mol.entities;

import pl.lodz.p.it.ssbd2020.ssbd04.mob.entities.Benefit;
import pl.lodz.p.it.ssbd2020.ssbd04.utils.AbstractEntity;

import javax.persistence.*;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * Informacje o klasie siedzenia i związanymi z nią benefitami
 */
@Entity
@Table(
        name = "seat_class",
        uniqueConstraints = {
                @UniqueConstraint(name = SeatClass.CONSTRAINT_NAME, columnNames = "name")
        }
)
public class SeatClass extends AbstractEntity implements Serializable {
    public static final String CONSTRAINT_NAME = "seat_class_name_unique";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(updatable = false)
    private Long id;

    @NotNull
    @Size(min = 2, max = 30)
    @Column(nullable = false, length = 30)
    private String name;

    @Digits(integer = 7, fraction = 2)
    @NotNull
    @Column(precision = 7, scale = 2, nullable = false)
    private BigDecimal price;

    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH})
    @JoinTable(
            name = "seat_class_benefits",
            joinColumns = @JoinColumn(name = "seat_class_id", foreignKey = @ForeignKey(name = "seat_class_id")),
            inverseJoinColumns = @JoinColumn(name = "benefit_id", foreignKey = @ForeignKey(name = "benefit_id")),
            indexes = {
                @Index(name = "benefit_fk", columnList = "benefit_id"),
                @Index(name = "seat_class_fk", columnList = "seat_class_id")
            },
            uniqueConstraints = @UniqueConstraint(
                    columnNames = {"seat_class_id", "benefit_id"},
                    name = "seat_class_benefit_seat_benefit_unique"
            )
    )
    private Set<Benefit> benefits = new HashSet<>();

    public SeatClass() {
    }

    public SeatClass(@NotNull @Size(min = 2, max = 30) String name, @Digits(integer = 7, fraction = 2) @NotNull BigDecimal price, Set<Benefit> benefits) {
        this.name = name;
        this.price = price;
        this.benefits = benefits;
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

    public Set<Benefit> getBenefits() {
        return benefits;
    }

    public void setBenefits(Set<Benefit> listOfBenefits) {
        this.benefits = listOfBenefits;
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
        return name.equals(seatClass.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }
}
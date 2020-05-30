package pl.lodz.p.it.ssbd2020.ssbd04.entities;

import pl.lodz.p.it.ssbd2020.ssbd04.common.AbstractEntity;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.Objects;

import static pl.lodz.p.it.ssbd2020.ssbd04.entities.Airport.CONSTRAINT_CODE;

/**
 * Klasa lotniska. Posiada informacje o nazwe, kodzie lotniska, mieście i państwie, w którym się znajduje
 */

@NamedQueries({
        @NamedQuery(name = "Airport.findByQuery",
            query = "SELECT airport from Airport airport WHERE LOWER(airport.code) LIKE LOWER(CONCAT('%', :code, '%')) " +
                    "AND LOWER(airport.name) LIKE LOWER(CONCAT('%', :name, '%')) AND LOWER(airport.city) LIKE LOWER(CONCAT('%', :city, '%')) " +
                    "AND LOWER(airport.country) LIKE LOWER(CONCAT('%', :country, '%'))"),
        @NamedQuery(name = "Airport.findById",
                query = "SELECT airport from Airport airport WHERE airport.id = :id")
})
@Entity
@Table(
        uniqueConstraints = {
                @UniqueConstraint(columnNames = "code", name = CONSTRAINT_CODE),
        }
)
public class Airport extends AbstractEntity implements Serializable {
    public static final String CONSTRAINT_CODE = "airport_code_unique";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false)
    private Long id;

    @Column(nullable = false, updatable = false)
    @NotNull
    private String code;

    @NotNull
    @Size(min = 2, max = 32)
    @Column(nullable = false, length = 32)
    private String name;

    @NotNull
    @Size(min = 2, max = 32)
    @Column(nullable = false, length = 32)
    private String country;

    @NotNull
    @Size(min = 2, max = 32)
    @Column(nullable = false, length = 32)
    private String city;

    public Airport() {
    }

    public Airport(String code, @NotNull @Size(min = 2, max = 32) String name, @NotNull @Size(min = 2, max = 32)
            String country, @NotNull @Size(min = 2, max = 32) String city) {
        this.code = code;
        this.name = name;
        this.country = country;
        this.city = city;
    }

    public Long getId() {
        return id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Airport airport = (Airport) o;
        return code.equals(airport.code);
    }

    @Override
    public int hashCode() {
        return Objects.hash(code);
    }

    @Override
    public String toString() {
        return "Airport{" +
                "id=" + id +
                ", version=" + getVersion() +
                "}";
    }
}

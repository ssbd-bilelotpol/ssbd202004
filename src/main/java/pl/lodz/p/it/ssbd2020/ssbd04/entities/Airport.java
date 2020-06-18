package pl.lodz.p.it.ssbd2020.ssbd04.entities;

import pl.lodz.p.it.ssbd2020.ssbd04.common.AbstractEntity;
import pl.lodz.p.it.ssbd2020.ssbd04.validation.AirportCity;
import pl.lodz.p.it.ssbd2020.ssbd04.validation.AirportCode;
import pl.lodz.p.it.ssbd2020.ssbd04.validation.AirportCountry;
import pl.lodz.p.it.ssbd2020.ssbd04.validation.AirportName;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
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
        @NamedQuery(name = "Airport.findByCode",
                query = "SELECT airport from Airport airport WHERE airport.code = UPPER(:code)"),
        @NamedQuery(name = "Airport.findByMatchingCode",
                query = "SELECT airport from Airport airport WHERE UPPER(airport.code) LIKE UPPER(CONCAT('%', :phrase, '%'))"),
        @NamedQuery(name = "Airport.getUniqueCities",
            query = "SELECT DISTINCT airport.city from Airport airport"),
        @NamedQuery(name = "Airport.getUniqueCountries",
            query = "SELECT DISTINCT airport.country from Airport airport")
})
@Entity
@Table(
        uniqueConstraints = {
                @UniqueConstraint(columnNames = "code", name = CONSTRAINT_CODE),
        }
)
public class Airport extends AbstractEntity implements Serializable {
    public static final String CONSTRAINT_CODE = "airport_code_unique";
    public static final String CONSTRAINT_SRC_IN_USE = "connection_airport_src_fk";
    public static final String CONSTRAINT_DST_IN_USE = "connection_airport_dst_fk";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false)
    private Long id;

    @Column(nullable = false, updatable = false)
    @NotNull
    @AirportCode
    private String code;

    @NotNull
    @Size(min = 2, max = 32)
    @Column(nullable = false, length = 32)
    @AirportName
    private String name;

    @NotNull
    @Size(min = 2, max = 32)
    @Column(nullable = false, length = 32, updatable = false)
    @AirportCountry
    private String country;

    @NotNull
    @Size(min = 2, max = 32)
    @Column(nullable = false, length = 32, updatable = false)
    @AirportCity
    private String city;

    public Airport() {
    }

    public Airport(@NotBlank @AirportCode String code, @NotNull @AirportName @Size(min = 2, max = 32) String name,
                   @NotNull @AirportCountry @Size(min = 2, max = 32) String country, @NotNull @AirportCity @Size(min = 2, max = 32) String city) {
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

package pl.lodz.p.it.ssbd2020.ssbd04.mol.entities;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.Objects;

/**
 * Klasa lotniska. Posiada informacje o nazwe, kodzie lotniska, mieście i państwie, w którym się znajduje
 */

@Entity
public class Airport implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false)
    private Long id;

    @Column(nullable = false)
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

    @Version
    private Long version;

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
        if (!(o instanceof Airport)) return false;
        Airport airport = (Airport) o;
        return Objects.equals(code, airport.code) &&
                Objects.equals(name, airport.name) &&
                Objects.equals(country, airport.country) &&
                Objects.equals(city, airport.city) &&
                Objects.equals(version, airport.version);
    }

    @Override
    public int hashCode() {
        return Objects.hash(code, name, country, city, version);
    }
}

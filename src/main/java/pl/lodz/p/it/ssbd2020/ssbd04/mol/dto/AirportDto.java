package pl.lodz.p.it.ssbd2020.ssbd04.mol.dto;

import pl.lodz.p.it.ssbd2020.ssbd04.entities.Airport;
import pl.lodz.p.it.ssbd2020.ssbd04.security.Signable;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * Reprezentuje lotnisko
 */
public class AirportDto implements Signable {

    private Long id;

    @NotNull
    private String code;

    @NotNull
    @Size(min = 2, max = 32)
    private String name;

    @NotNull
    @Size(min = 2, max = 32)
    private String country;

    @NotNull
    @Size(min = 2, max = 32)
    private String city;

    private Long version;


    public AirportDto() {}

    public AirportDto(Airport airport) {
        this.version = airport.getVersion();
        this.id = airport.getId();
        this.code = airport.getCode();
        this.name = airport.getName();
        this.country = airport.getCountry();
        this.city = airport.getCity();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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
    public String createMessage() {
        return String.format("%d.%d.%s", this.version, this.id, this.code);
    }

    @Override
    public String toString() {
        return "AirportDto{" +
                "id=" + id +
                ", code='" + code + '\'' +
                ", name='" + name + '\'' +
                ", country='" + country + '\'' +
                ", city='" + city + '\'' +
                ", version=" + version +
                '}';
    }
}

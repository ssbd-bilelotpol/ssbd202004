package pl.lodz.p.it.ssbd2020.ssbd04.mol.dto;

import pl.lodz.p.it.ssbd2020.ssbd04.entities.Airport;
import pl.lodz.p.it.ssbd2020.ssbd04.security.Signable;
import pl.lodz.p.it.ssbd2020.ssbd04.validation.AirportCity;
import pl.lodz.p.it.ssbd2020.ssbd04.validation.AirportCode;
import pl.lodz.p.it.ssbd2020.ssbd04.validation.AirportCountry;
import pl.lodz.p.it.ssbd2020.ssbd04.validation.AirportName;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * Reprezentuje lotnisko.
 */
public class AirportDto implements Signable {

    @NotBlank
    @AirportCode
    private String code;

    @NotNull
    @AirportName
    @Size(min = 2, max = 32)
    private String name;

    @NotNull
    @AirportCountry
    @Size(min = 2, max = 32)
    private String country;

    @NotNull
    @AirportCity
    @Size(min = 2, max = 32)
    private String city;

    private Long version;


    public AirportDto() {}

    public AirportDto(Airport airport) {
        this.version = airport.getVersion();
        this.code = airport.getCode();
        this.name = airport.getName();
        this.country = airport.getCountry();
        this.city = airport.getCity();
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

    public Long getVersion() {
        return version;
    }

    @Override
    public String createMessage() {
        return String.format("%d.%s", this.version, this.code);
    }

    @Override
    public String toString() {
        return "AirportDto{" +
                ", code='" + code + '\'' +
                ", name='" + name + '\'' +
                ", country='" + country + '\'' +
                ", city='" + city + '\'' +
                ", version=" + version +
                '}';
    }
}

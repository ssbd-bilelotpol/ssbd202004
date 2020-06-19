package pl.lodz.p.it.ssbd2020.ssbd04.mob.dto;

import pl.lodz.p.it.ssbd2020.ssbd04.entities.Airport;

/**
 * Reprezentuje lotnisko.
 */
public class AirportDto {

    private String code;
    private String name;
    private String country;
    private String city;

    public AirportDto() {}

    public AirportDto(Airport airport) {
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
}

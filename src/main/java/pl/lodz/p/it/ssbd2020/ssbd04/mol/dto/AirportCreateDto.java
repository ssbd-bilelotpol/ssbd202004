package pl.lodz.p.it.ssbd2020.ssbd04.mol.dto;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;


/**
 * Reprezentuje formularz tworzenia nowego lotniska.
 */
public class AirportCreateDto {

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

    public AirportCreateDto() {}

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
    public String toString() {
        return "AirportCreateDto{" +
                "code='" + code + '\'' +
                ", name='" + name + '\'' +
                ", country='" + country + '\'' +
                ", city='" + city + '\'' +
                '}';
    }
}

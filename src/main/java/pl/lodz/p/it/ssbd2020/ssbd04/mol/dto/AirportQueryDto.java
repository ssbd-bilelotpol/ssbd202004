package pl.lodz.p.it.ssbd2020.ssbd04.mol.dto;

/**
 * Reprezentuje kryteria wyszukiwania lotnisk
 */
public class AirportQueryDto {

    private String code;

    private String name;

    private String country;

    private String city;

    public AirportQueryDto() {}

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
        return "AirportQueryDto{" +
                "code='" + code + '\'' +
                ", name='" + name + '\'' +
                ", country='" + country + '\'' +
                ", city='" + city + '\'' +
                '}';
    }
}

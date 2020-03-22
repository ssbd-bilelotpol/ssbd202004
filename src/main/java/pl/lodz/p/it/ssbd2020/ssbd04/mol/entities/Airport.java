package pl.lodz.p.it.ssbd2020.ssbd04.mol.entities;

public class Airport {
    private long id;
    private String code;
    private String name;
    private String country;
    private String city;

    public Airport(long id, String code, String name, String country, String city) {
        this.id = id;
        this.code = code;
        this.name = name;
        this.country = country;
        this.city = city;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
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
}

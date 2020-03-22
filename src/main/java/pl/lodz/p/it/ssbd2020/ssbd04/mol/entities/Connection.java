package pl.lodz.p.it.ssbd2020.ssbd04.mol.entities;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigInteger;
import java.util.List;

/**
 * Informacje o połączeniu między dwoma miejscami, lotach, które te połączenie obsługują, lotnisku, z którego
 * te loty odlatują oraz bazowej cenie połączenia
 */

@Entity
@Table(name = "Connection")
public class Connection implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id", updatable = false, nullable = false)
    private long id;
    private String destination;
    private String source;

    @OneToMany(targetEntity = Flight.class)
    private List<Flight> flights;
    private float basePrice;

    @ManyToOne(targetEntity = Airport.class)
    private Airport airport;

    private BigInteger version;

    public Connection() {}

    public Connection(String destination, String source, List<Flight> flights, float basePrice, Airport airport, BigInteger version) {
        this.destination = destination;
        this.source = source;
        this.flights = flights;
        this.basePrice = basePrice;
        this.airport = airport;
        this.version = version;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public List<Flight> getFlights() {
        return flights;
    }

    public void setFlights(List<Flight> flights) {
        this.flights = flights;
    }

    public float getBasePrice() {
        return basePrice;
    }

    public void setBasePrice(float basePrice) {
        this.basePrice = basePrice;
    }

    public Airport getAirport() {
        return airport;
    }

    public void setAirport(Airport airport) {
        this.airport = airport;
    }

    public BigInteger getVersion() {
        return version;
    }

    public void setVersion(BigInteger version) {
        this.version = version;
    }
}

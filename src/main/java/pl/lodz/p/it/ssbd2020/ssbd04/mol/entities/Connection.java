package pl.lodz.p.it.ssbd2020.ssbd04.mol.entities;

import java.util.List;

public class Connection {
    private long id;
    private String destination;
    private String source;
    private List<Flight> flights;
    private float basePrice;

    public Connection(long id, String destination, String source, List<Flight> flights, float basePrice) {
        this.id = id;
        this.destination = destination;
        this.source = source;
        this.flights = flights;
        this.basePrice = basePrice;
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
}

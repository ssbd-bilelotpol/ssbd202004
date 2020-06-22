package pl.lodz.p.it.ssbd2020.ssbd04.mob.dto;

import pl.lodz.p.it.ssbd2020.ssbd04.entities.Connection;
import pl.lodz.p.it.ssbd2020.ssbd04.security.Signable;

import java.math.BigDecimal;

/**
 * Reprezentuje połączenie
 */
public class ConnectionDto implements Signable {

    private Long id;

    private BigDecimal price;

    private AirportDto destination;

    private AirportDto source;

    private Long version;

    public ConnectionDto() {
    }

    public ConnectionDto(Connection connection) {
        this.id = connection.getId();
        this.price = connection.getBasePrice();
        this.destination = new AirportDto(connection.getDestination());
        this.source = new AirportDto(connection.getSource());
        this.version = connection.getVersion();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public void setVersion(Long version) {
        this.version = version;
    }

    public AirportDto getSource() {
        return source;
    }

    public void setSource(AirportDto source) {
        this.source = source;
    }

    public AirportDto getDestination() {
        return destination;
    }

    public void setDestination(AirportDto destination) {
        this.destination = destination;
    }

    @Override
    public String createMessage() {
        return String.format("%d.%s.%s", version, source.getCode(), destination.getCode());
    }
}

package pl.lodz.p.it.ssbd2020.ssbd04.mol.dto;

import pl.lodz.p.it.ssbd2020.ssbd04.entities.Connection;
import pl.lodz.p.it.ssbd2020.ssbd04.security.Signable;

import javax.json.bind.annotation.JsonbTransient;
import java.math.BigDecimal;

/**
 * Reprezentuje połączenie
 */
public class ConnectionDto implements Signable {

    private Long id;

    private BigDecimal basePrice;

    private AirportDto destination;

    private AirportDto source;

    @JsonbTransient
    private Long version;

    public ConnectionDto() {
    }

    public ConnectionDto(Connection connection) {
        this.id = connection.getId();
        this.basePrice = connection.getBasePrice();
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

    public BigDecimal getBasePrice() {
        return basePrice;
    }

    public void setBasePrice(BigDecimal price) {
        this.basePrice = price;
    }

    public void setVersion(Long version) {
        this.version = version;
    }

    public Long getVersion() {
        return version;
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

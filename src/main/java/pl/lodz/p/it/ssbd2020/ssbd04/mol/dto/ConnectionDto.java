package pl.lodz.p.it.ssbd2020.ssbd04.mol.dto;

import pl.lodz.p.it.ssbd2020.ssbd04.entities.Connection;
import pl.lodz.p.it.ssbd2020.ssbd04.security.Signable;

import javax.persistence.Id;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

/**
 * Reprezentuje połączenie
 */
public class ConnectionDto implements Signable {

    @Id
    private Long id;

    @Digits(integer = 7, fraction = 2)
    @DecimalMin("0.0")
    @NotNull
    private BigDecimal basePrice;

    @NotNull
    private AirportDto destination;

    @NotNull
    private AirportDto source;

    private Long version;

    public ConnectionDto(Connection connection) {
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

    public void setBasePrice(BigDecimal basePrice) {
        this.basePrice = basePrice;
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

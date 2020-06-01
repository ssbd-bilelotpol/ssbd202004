package pl.lodz.p.it.ssbd2020.ssbd04.mol.dto;

import pl.lodz.p.it.ssbd2020.ssbd04.entities.Airport;
import pl.lodz.p.it.ssbd2020.ssbd04.entities.Connection;
import pl.lodz.p.it.ssbd2020.ssbd04.security.Signable;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

/**
 * Reprezentuje połączenie
 */
public class ConnectionDto implements Signable {

    @NotNull
    private BigDecimal basePrice;

    @NotNull
    private Airport destination;

    @NotNull
    private Airport source;

    private Long version;

    public ConnectionDto(Connection connection) {
        this.basePrice = connection.getBasePrice();
        this.destination = connection.getDestination();
        this.source = connection.getSource();
        this.version = connection.getVersion();
    }

    public BigDecimal getBasePrice() {
        return basePrice;
    }

    public void setBasePrice(BigDecimal basePrice) {
        this.basePrice = basePrice;
    }

    public Long getVersion() {
        return version;
    }

    public void setVersion(Long version) {
        this.version = version;
    }

    public Airport getSource() {
        return source;
    }

    public void setSource(Airport source) {
        this.source = source;
    }

    public Airport getDestination() {
        return destination;
    }

    public void setDestination(Airport destination) {
        this.destination = destination;
    }

    @Override
    public String createMessage() {
        return String.format("%d.%d.%d", this.version, this.source.getId(), this.destination.getId());
    }
}

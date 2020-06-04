package pl.lodz.p.it.ssbd2020.ssbd04.mol.dto;

import pl.lodz.p.it.ssbd2020.ssbd04.security.Signable;
import pl.lodz.p.it.ssbd2020.ssbd04.validation.AirportCode;

import javax.validation.constraints.Digits;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

/**
 * Reprezentuje połączenie
 */
public class ConnectionCreateDto implements Signable {

    @Digits(integer = 7, fraction = 2)
    @NotNull
    private BigDecimal basePrice;

    @AirportCode
    @NotNull
    private String destinationCode;

    @AirportCode
    @NotNull
    private String sourceCode;

    public ConnectionCreateDto(BigDecimal basePrice, String sourceCode, String destinationCode) {
        this.basePrice = basePrice;
        this.destinationCode = destinationCode;
        this.sourceCode = sourceCode;
    }

    public BigDecimal getBasePrice() {
        return basePrice;
    }

    public void setBasePrice(BigDecimal basePrice) {
        this.basePrice = basePrice;
    }

    public String getSourceCode() {
        return sourceCode;
    }

    public void setSourceCode(String sourceCode) {
        this.sourceCode = sourceCode;
    }

    public String getDestinationCode() {
        return destinationCode;
    }

    public void setDestinationCode(String destinationCode) {
        this.destinationCode = destinationCode;
    }

    @Override
    public String createMessage() {
        return String.format("%d.%d.%d", sourceCode, destinationCode);
    }
}

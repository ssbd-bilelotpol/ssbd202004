package pl.lodz.p.it.ssbd2020.ssbd04.mol.dto;

import pl.lodz.p.it.ssbd2020.ssbd04.validation.AirportCode;

import javax.validation.constraints.NotNull;

/**
 * Reprezentuje kryteria wyszukiwania połączeń
 */
public class ConnectionQueryDto {

    @AirportCode
    @NotNull
    private String destinationCode;

    @AirportCode
    @NotNull
    private String sourceCode;

    public ConnectionQueryDto(String destinationCode, String sourceCode) {
        this.destinationCode = destinationCode;
        this.sourceCode = sourceCode;
    }

    public String getDestinationCode() {
        return destinationCode;
    }

    public void setDestinationCode(String destinationCode) {
        this.destinationCode = destinationCode;
    }

    public String getSourceCode() {
        return destinationCode;
    }

    public void setSourceCode(String source) {
        this.destinationCode = destinationCode;
    }
}

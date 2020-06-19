package pl.lodz.p.it.ssbd2020.ssbd04.mob.dto;

import java.io.Serializable;
import java.math.BigDecimal;

public class ReportDto implements Serializable {

    private Long id;

    private String sourceAirport;

    private String destinationAirport;

    private BigDecimal profit;

    public ReportDto() {}

    public ReportDto(Long id, String sourceAirport, String destinationAirport, BigDecimal profit) {
        this.id = id;
        this.sourceAirport = sourceAirport;
        this.destinationAirport = destinationAirport;
        this.profit = profit;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSourceAirport() {
        return sourceAirport;
    }

    public void setSourceAirport(String sourceAirport) {
        this.sourceAirport = sourceAirport;
    }

    public String getDestinationAirport() {
        return destinationAirport;
    }

    public void setDestinationAirport(String destinationAirport) {
        this.destinationAirport = destinationAirport;
    }

    public BigDecimal getProfit() {
        return profit;
    }

    public void setProfit(BigDecimal profit) {
        this.profit = profit;
    }
}

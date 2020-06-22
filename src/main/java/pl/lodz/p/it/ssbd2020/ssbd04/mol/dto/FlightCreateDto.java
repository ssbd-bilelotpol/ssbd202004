package pl.lodz.p.it.ssbd2020.ssbd04.mol.dto;

import pl.lodz.p.it.ssbd2020.ssbd04.validation.DateOrder;
import pl.lodz.p.it.ssbd2020.ssbd04.validation.FlightCode;

import javax.json.bind.annotation.JsonbDateFormat;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Digits;
import javax.validation.constraints.Future;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * Reprezentuje dane nowego lotu
 */
@DateOrder(before = "departureTime", after = "arrivalTime")
public class FlightCreateDto {
    @NotNull
    @FlightCode
    private String flightCode;
    @Digits(integer = 7, fraction = 2)
    @DecimalMin("0.0")
    private BigDecimal price;
    @NotNull
    private Long connectionId;
    @NotNull
    private Long airplaneSchemaId;
    @NotNull
    @Future
    @JsonbDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSXXX")
    private LocalDateTime departureTime;
    @NotNull
    @Future
    @JsonbDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSXXX")
    private LocalDateTime arrivalTime;

    public FlightCreateDto() {
    }

    public String getFlightCode() {
        return flightCode;
    }

    public void setFlightCode(String flightCode) {
        this.flightCode = flightCode;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Long getConnectionId() {
        return connectionId;
    }

    public void setConnectionId(Long connectionId) {
        this.connectionId = connectionId;
    }

    public Long getAirplaneSchemaId() {
        return airplaneSchemaId;
    }

    public void setAirplaneSchemaId(Long airplaneSchemaId) {
        this.airplaneSchemaId = airplaneSchemaId;
    }

    public LocalDateTime getDepartureTime() {
        return departureTime;
    }

    public void setDepartureTime(LocalDateTime departureTime) {
        this.departureTime = departureTime;
    }

    public LocalDateTime getArrivalTime() {
        return arrivalTime;
    }

    public void setArrivalTime(LocalDateTime arrivalTime) {
        this.arrivalTime = arrivalTime;
    }
}

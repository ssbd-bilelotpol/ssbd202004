package pl.lodz.p.it.ssbd2020.ssbd04.mol.dto;

import pl.lodz.p.it.ssbd2020.ssbd04.validation.DateOrder;

import javax.json.bind.annotation.JsonbDateFormat;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Digits;
import javax.validation.constraints.Future;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * Reprezentuje dane edycji lotu
 */
@DateOrder(before = "departureTime", after = "arrivalTime")
public class FlightEditDto {
    @Digits(integer = 7, fraction = 2)
    @DecimalMin("0.0")
    private BigDecimal price;
    @NotNull
    @Future
    @JsonbDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSXXX")
    private LocalDateTime departureTime;
    @NotNull
    @Future
    @JsonbDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSXXX")
    private LocalDateTime arrivalTime;
    @NotNull
    private Boolean active;

    public FlightEditDto() {
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
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

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }
}

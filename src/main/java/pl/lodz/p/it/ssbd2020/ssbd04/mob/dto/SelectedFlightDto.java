package pl.lodz.p.it.ssbd2020.ssbd04.mob.dto;

import javax.json.bind.annotation.JsonbDateFormat;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Digits;
import javax.validation.constraints.Future;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public class SelectedFlightDto {

    @NotNull
    private String code;

    @NotNull
    @Future
    @JsonbDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSXXX")
    private LocalDateTime expectedDepartureTime;

    @NotNull
    private List<SelectedSeatDto> seats;

    @Digits(integer = 7, fraction = 2)
    @DecimalMin("0.0")
    @NotNull
    private BigDecimal expectedPrice;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public List<SelectedSeatDto> getSeats() {
        return seats;
    }

    public void setSeats(List<SelectedSeatDto> seats) {
        this.seats = seats;
    }

    public BigDecimal getExpectedPrice() {
        return expectedPrice;
    }

    public void setExpectedPrice(BigDecimal expectedPrice) {
        this.expectedPrice = expectedPrice;
    }

    public LocalDateTime getExpectedDepartureTime() {
        return expectedDepartureTime;
    }

    public void setExpectedDepartureTime(LocalDateTime expectedDepartureTime) {
        this.expectedDepartureTime = expectedDepartureTime;
    }

}

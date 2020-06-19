package pl.lodz.p.it.ssbd2020.ssbd04.mob.dto;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

public class SelectedSeatDto {

    @Digits(integer = 7, fraction = 2)
    @DecimalMin("0.0")
    @NotNull
    private BigDecimal expectedPrice;

    @NotNull
    private Long id;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public BigDecimal getExpectedPrice() {
        return expectedPrice;
    }

    public void setExpectedPrice(BigDecimal expectedPrice) {
        this.expectedPrice = expectedPrice;
    }

}

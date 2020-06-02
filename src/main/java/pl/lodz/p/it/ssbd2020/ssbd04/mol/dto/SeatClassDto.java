package pl.lodz.p.it.ssbd2020.ssbd04.mol.dto;

import pl.lodz.p.it.ssbd2020.ssbd04.entities.Benefit;
import pl.lodz.p.it.ssbd2020.ssbd04.entities.SeatClass;
import pl.lodz.p.it.ssbd2020.ssbd04.security.Signable;

import javax.persistence.*;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Przenosi dane klasy miejsc do warstwy prezentacji.
 */
public class SeatClassDto implements Signable {

    @NotNull
    @Size(min = 2, max = 30)
    private String name;

    @Digits(integer = 7, fraction = 2)
    @NotNull
    private BigDecimal price;

    private Set<BenefitDto> benefits;

    private Long version;

    public SeatClassDto() {
    }

    public SeatClassDto(SeatClass seatClass) {
        this.name = seatClass.getName();
        this.price = seatClass.getPrice();
        this.version = seatClass.getVersion();
        this.benefits = seatClass.getBenefits().stream()
                .map(b -> new BenefitDto(b)).collect(Collectors.toSet());
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Set<BenefitDto> getBenefits() {
        return benefits;
    }

    public void setBenefits(Set<BenefitDto> benefits) {
        this.benefits = benefits;
    }

    @Override
    public String createMessage() {
        return String.format("%d.%s", this.version, this.name);
    }
}

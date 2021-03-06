package pl.lodz.p.it.ssbd2020.ssbd04.mob.dto;

import pl.lodz.p.it.ssbd2020.ssbd04.entities.SeatClass;
import pl.lodz.p.it.ssbd2020.ssbd04.entities.SeatClassColor;
import pl.lodz.p.it.ssbd2020.ssbd04.mol.dto.BenefitDto;

import java.math.BigDecimal;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Przenosi dane klasy miejsc do warstwy prezentacji.
 */
public class SeatClassDto {

    private String name;
    private BigDecimal price;
    private Set<BenefitDto> benefits;
    private Long version;
    private Set<BenefitDto> existingBenefits;
    private SeatClassColor color;

    public SeatClassDto() {
    }

    public SeatClassDto(SeatClass seatClass) {
        this.name = seatClass.getName();
        this.price = seatClass.getPrice();
        this.version = seatClass.getVersion();
        this.benefits = seatClass.getBenefits().stream()
                .map(b -> new BenefitDto(b)).collect(Collectors.toSet());
        this.color = seatClass.getColor();
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

    public Long getVersion() {
        return version;
    }

    public Set<BenefitDto> getExistingBenefits() {
        return existingBenefits;
    }

    public void setExistingBenefits(Set<BenefitDto> existingBenefits) {
        this.existingBenefits = existingBenefits;
    }

    public SeatClassColor getColor() {
        return color;
    }

    public void setColor(SeatClassColor color) {
        this.color = color;
    }

}

package pl.lodz.p.it.ssbd2020.ssbd04.mob.dto;

import pl.lodz.p.it.ssbd2020.ssbd04.entities.Benefit;

/**
 * Przenosi dane dodatku do warstwy prezentacji.
 */
public class BenefitDto {

    private String name;
    private String description;

    public BenefitDto() {
    }

    public BenefitDto(Benefit benefit) {
        this.name = benefit.getName();
        this.description = benefit.getDescription();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}

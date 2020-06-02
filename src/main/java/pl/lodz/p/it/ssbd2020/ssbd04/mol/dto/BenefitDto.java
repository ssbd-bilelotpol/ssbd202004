package pl.lodz.p.it.ssbd2020.ssbd04.mol.dto;

import pl.lodz.p.it.ssbd2020.ssbd04.entities.Benefit;
import pl.lodz.p.it.ssbd2020.ssbd04.security.Signable;

import javax.persistence.Column;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * Przenosi dane dodatku do warstwy prezentacji.
 */
public class BenefitDto implements Signable {

    @NotNull
    @Size(min = 1, max = 128)
    private String name;

    @NotNull
    @Size(min = 5, max = 255)
    private String description;

    private Long version;

    public BenefitDto() {
    }

    public BenefitDto(Benefit benefit) {
        this.name = benefit.getName();
        this.description = benefit.getDescription();
        this.version = benefit.getVersion();
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

    @Override
    public String createMessage() {
        return String.format("%d.%s", this.version, this.name);
    }
}

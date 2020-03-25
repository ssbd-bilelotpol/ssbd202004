package pl.lodz.p.it.ssbd2020.ssbd04.mok.entities.access_levels;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * Klasa encyjna reprezentująca poziom dostępu obsługi klienta
 */
@Entity
@Table(name = "customer_service_access_level")
public class CustomerServiceAccessLevel extends AccountAccessLevel implements Serializable {

    @NotNull
    @ManyToOne
    @JoinColumn(name = "created_by", updatable = false, nullable = false)
    private AdminAccessLevel createdBy;

    public CustomerServiceAccessLevel() {
    }

    public CustomerServiceAccessLevel(Integer priority, Long version, @NotNull AdminAccessLevel createdBy) {
        super(priority, version);
        this.createdBy = createdBy;
    }

    public AdminAccessLevel getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(AdminAccessLevel createdBy) {
        this.createdBy = createdBy;
    }
}

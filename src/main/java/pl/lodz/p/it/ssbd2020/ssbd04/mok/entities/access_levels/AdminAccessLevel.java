package pl.lodz.p.it.ssbd2020.ssbd04.mok.entities.access_levels;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * Klasa encyjna reprezentująca poziom dostępu administratora
 */
@Entity
@Table(name = "admin_access_level")
public class AdminAccessLevel extends AccountAccessLevel implements Serializable {
    public AdminAccessLevel() {
    }

    public AdminAccessLevel(Integer priority, Long version) {
        super(priority, version);
    }
}

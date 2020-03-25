package pl.lodz.p.it.ssbd2020.ssbd04.mok.entities.access_levels;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * Klasa encyjna reprezentująca poziom dostępu klienta
 */
@Entity
@Table(name = "client_access_level")
public class ClientAccessLevel extends AccountAccessLevel implements Serializable {

    public ClientAccessLevel() {
    }

    public ClientAccessLevel(Integer priority, Long version) {
        super(priority, version);
    }
}

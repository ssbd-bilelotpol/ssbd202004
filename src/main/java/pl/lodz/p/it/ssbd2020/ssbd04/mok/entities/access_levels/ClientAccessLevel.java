package pl.lodz.p.it.ssbd2020.ssbd04.mok.entities.access_levels;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import java.io.Serializable;

/**
 * Klasa encyjna reprezentująca poziom dostępu klienta
 */
@Entity
@DiscriminatorValue("client")
public class ClientAccessLevel extends AccountAccessLevel implements Serializable {

    @Override
    public String getType() {
        return "client";
    }
}

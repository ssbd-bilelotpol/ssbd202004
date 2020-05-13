package pl.lodz.p.it.ssbd2020.ssbd04.entities.access_levels;

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

    @Override
    public String toString() {
        return "ClientAccessLevel{" + super.toString() + "}";
    }
}

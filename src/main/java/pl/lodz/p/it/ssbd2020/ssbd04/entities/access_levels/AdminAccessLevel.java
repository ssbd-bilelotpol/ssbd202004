package pl.lodz.p.it.ssbd2020.ssbd04.entities.access_levels;

import pl.lodz.p.it.ssbd2020.ssbd04.common.Group;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import java.io.Serializable;

/**
 * Klasa encyjna reprezentująca poziom dostępu administratora
 */
@Entity
@DiscriminatorValue(Group.ADMIN)
public class AdminAccessLevel extends AccountAccessLevel implements Serializable {

    @Override
    public String getType() {
        return Group.ADMIN;
    }

    @Override
    public String toString() {
        return "AdminAccessLevel{" + super.toString() + "}";
    }
}

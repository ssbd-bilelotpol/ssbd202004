package pl.lodz.p.it.ssbd2020.ssbd04.entities.access_levels;

import pl.lodz.p.it.ssbd2020.ssbd04.common.Group;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import java.io.Serializable;

/**
 * Klasa encyjna reprezentująca poziom dostępu menadżera zasobów
 */
@Entity
@DiscriminatorValue(Group.MANAGER)
public class ManagerAccessLevel extends AccountAccessLevel implements Serializable {

    @Override
    public String getType() {
        return Group.MANAGER;
    }

    @Override
    public String toString() {
        return "ManagerAccessLevel{" + super.toString() + "}";
    }
}

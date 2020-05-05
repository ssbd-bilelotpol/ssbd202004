package pl.lodz.p.it.ssbd2020.ssbd04.entities.access_levels;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import java.io.Serializable;

/**
 * Klasa encyjna reprezentująca poziom dostępu menadżera zasobów
 */
@Entity
@DiscriminatorValue("manager")
public class ManagerAccessLevel extends AccountAccessLevel implements Serializable {

    @Override
    public String getType() {
        return "manager";
    }

    @Override
    public String toString() {
        return "ManagerAccessLevel{id="+getId()+"}";
    }
}

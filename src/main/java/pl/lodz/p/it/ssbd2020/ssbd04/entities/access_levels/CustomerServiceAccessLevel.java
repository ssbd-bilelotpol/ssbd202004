package pl.lodz.p.it.ssbd2020.ssbd04.entities.access_levels;

import pl.lodz.p.it.ssbd2020.ssbd04.common.Group;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import java.io.Serializable;

/**
 * Klasa encyjna reprezentująca poziom dostępu obsługi klienta
 */
@Entity
@DiscriminatorValue(Group.CUSTOMER_SERVICE)
public class CustomerServiceAccessLevel extends AccountAccessLevel implements Serializable {

    @Override
    public String getType() {
        return Group.CUSTOMER_SERVICE;
    }

    @Override
    public String toString() {
        return "CustomerServiceAccessLevel{" + super.toString() + "}";
    }
}

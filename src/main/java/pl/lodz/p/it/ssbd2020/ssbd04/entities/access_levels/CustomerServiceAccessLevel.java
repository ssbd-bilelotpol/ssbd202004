package pl.lodz.p.it.ssbd2020.ssbd04.entities.access_levels;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import java.io.Serializable;

/**
 * Klasa encyjna reprezentująca poziom dostępu obsługi klienta
 */
@Entity
@DiscriminatorValue("customer_service")
public class CustomerServiceAccessLevel extends AccountAccessLevel implements Serializable {

    @Override
    public String getType() {
        return "customer_service";
    }

    @Override
    public String toString() {
        return "CustomerServiceAccessLevel{id="+getId()+"}";
    }
}
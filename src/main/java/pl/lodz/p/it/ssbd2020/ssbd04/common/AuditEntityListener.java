package pl.lodz.p.it.ssbd2020.ssbd04.common;

import pl.lodz.p.it.ssbd2020.ssbd04.entities.Account;
import pl.lodz.p.it.ssbd2020.ssbd04.security.AuthContext;

import javax.enterprise.inject.spi.CDI;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;

/**
 * Służy do audytowania encji.
 * Gdy obiekt jest tworzony, bądź aktualizowany ustawiany jest wykonawca operacji.
 */
public class AuditEntityListener {

    /**
     * Ustawia wykonawce operacji tworzenia encji.
     *
     * @param object obiekt encji.
     */
    @PrePersist
    public void prePersist(Object object) {
        AbstractEntity abstractEntity = (AbstractEntity) object;
        AuthContext authContext = CDI.current().select(AuthContext.class).get();
        Account currentUser = authContext.currentUser();
        abstractEntity.setCreatedBy(currentUser);
    }

    /**
     * Ustawia wykonawce operacji aktualizacji encji.
     *
     * @param object obiekt encji.
     */
    @PreUpdate
    public void preUpdate(Object object) {
        AbstractEntity abstractEntity = (AbstractEntity) object;
        AuthContext authContext = CDI.current().select(AuthContext.class).get();
        Account currentUser = authContext.currentUser();
        abstractEntity.setModifiedBy(currentUser);
    }
}

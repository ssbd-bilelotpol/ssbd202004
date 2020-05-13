package pl.lodz.p.it.ssbd2020.ssbd04.common;

import pl.lodz.p.it.ssbd2020.ssbd04.entities.Account;
import pl.lodz.p.it.ssbd2020.ssbd04.security.AuthContext;

import javax.enterprise.inject.spi.CDI;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;

public class AuditEntityListener {

    @PrePersist
    public void prePersist(Object object) {
        AbstractEntity abstractEntity = (AbstractEntity) object;
        AuthContext authContext = CDI.current().select(AuthContext.class).get();
        Account currentUser = authContext.currentUser();
        abstractEntity.setCreatedBy(currentUser);
    }

    @PreUpdate
    public void preUpdate(Object object) {
        AbstractEntity abstractEntity = (AbstractEntity) object;
        AuthContext authContext = CDI.current().select(AuthContext.class).get();
        Account currentUser = authContext.currentUser();
        abstractEntity.setModifiedBy(currentUser);
    }
}

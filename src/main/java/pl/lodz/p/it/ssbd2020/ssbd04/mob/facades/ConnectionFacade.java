package pl.lodz.p.it.ssbd2020.ssbd04.mob.facades;

import pl.lodz.p.it.ssbd2020.ssbd04.common.AbstractFacade;
import pl.lodz.p.it.ssbd2020.ssbd04.common.I18n;
import pl.lodz.p.it.ssbd2020.ssbd04.entities.Connection;
import pl.lodz.p.it.ssbd2020.ssbd04.exceptions.AppBaseException;
import pl.lodz.p.it.ssbd2020.ssbd04.exceptions.RepeatableOptimisticLockException;
import pl.lodz.p.it.ssbd2020.ssbd04.interceptors.TrackingInterceptor;
import pl.lodz.p.it.ssbd2020.ssbd04.security.Role;

import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Named;
import javax.interceptor.Interceptors;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

/**
 * Klasa definiująca operacje wykonywane na encjach klasy Connection
 * przez zarządcę encji w kontekście trwałości
 */
@Interceptors({TrackingInterceptor.class})
@Stateless(name = "ConnectionFacadeMOB")
@Named("ConnectionFacadeMOB")
@TransactionAttribute(TransactionAttributeType.MANDATORY)
public class ConnectionFacade extends AbstractFacade<Connection> {

    @PersistenceContext(unitName = "ssbd04mobPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public ConnectionFacade() {
        super(Connection.class);
    }

    @Override
    @RolesAllowed({Role.ReturnTicket, Role.ReturnAnyTicket, Role.CreateTicket})
    public void edit(Connection entity) throws AppBaseException {
        try {
            super.edit(entity);
        } catch (AppBaseException e) {
            if (e.getMessage().equals(I18n.DATABASE_OPTIMISTIC_LOCK)) {
                throw RepeatableOptimisticLockException.optimisticLock();
            }

            throw e;
        }
    }

    @Override
    @PermitAll
    public List<Connection> findAll() throws AppBaseException {
        return super.findAll();
    }
}

package pl.lodz.p.it.ssbd2020.ssbd04.mob.facades;

import pl.lodz.p.it.ssbd2020.ssbd04.common.AbstractFacade;
import pl.lodz.p.it.ssbd2020.ssbd04.entities.Flight;
import pl.lodz.p.it.ssbd2020.ssbd04.exceptions.AppBaseException;
import pl.lodz.p.it.ssbd2020.ssbd04.exceptions.FlightException;
import pl.lodz.p.it.ssbd2020.ssbd04.interceptors.TrackingInterceptor;

import javax.annotation.security.PermitAll;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.interceptor.Interceptors;
import javax.persistence.*;


@Interceptors({TrackingInterceptor.class})
@Stateless
@TransactionAttribute(TransactionAttributeType.MANDATORY)
public class FlightFacade extends AbstractFacade<Flight> {

    @PersistenceContext(unitName = "ssbd04mobPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public FlightFacade() {
        super(Flight.class);
    }

    @Override
    @PermitAll
    public Flight find(Object code) throws AppBaseException {
        try {
            TypedQuery<Flight> flightTypedQuery = em.createNamedQuery("Flight.findByCode", Flight.class);
            flightTypedQuery.setLockMode(LockModeType.PESSIMISTIC_READ);
            flightTypedQuery.setParameter("code", code);
            return flightTypedQuery.getSingleResult();
        } catch (NoResultException e) {
            throw FlightException.notFound();
        } catch (PersistenceException e) {
            throw AppBaseException.databaseOperation(e);
        }
    }

}

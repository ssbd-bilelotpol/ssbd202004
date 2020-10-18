package pl.lodz.p.it.ssbd2020.ssbd04.mob.facades;

import pl.lodz.p.it.ssbd2020.ssbd04.common.AbstractFacade;
import pl.lodz.p.it.ssbd2020.ssbd04.entities.Seat;
import pl.lodz.p.it.ssbd2020.ssbd04.exceptions.AppBaseException;
import pl.lodz.p.it.ssbd2020.ssbd04.exceptions.SeatException;
import pl.lodz.p.it.ssbd2020.ssbd04.interceptors.TrackingInterceptor;

import javax.annotation.security.PermitAll;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.interceptor.Interceptors;
import javax.persistence.*;

/**
 * Klasa definiująca operacje wykonywane na encjach klasy Seat
 * przez zarządcę encji w kontekście trwałości
 */
@Interceptors({TrackingInterceptor.class})
@Stateless(name = "SeatFacadeMOB")
@TransactionAttribute(TransactionAttributeType.MANDATORY)
public class SeatFacade extends AbstractFacade<Seat> {

    @PersistenceContext(unitName = "ssbd04mobPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public SeatFacade() {
        super(Seat.class);
    }

    @Override
    @PermitAll
    public Seat find(Object id) throws AppBaseException {
        try {
            TypedQuery<Seat> seatTypedQuery = em.createNamedQuery("Seat.findById", Seat.class);
            seatTypedQuery.setParameter("id", id);

            return seatTypedQuery.getSingleResult();
        } catch (NoResultException e) {
            throw SeatException.notFound(e);
        } catch (PersistenceException e) {
            throw AppBaseException.databaseOperation(e);
        }
    }

}

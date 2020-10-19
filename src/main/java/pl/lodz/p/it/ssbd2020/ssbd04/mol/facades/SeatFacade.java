package pl.lodz.p.it.ssbd2020.ssbd04.mol.facades;

import pl.lodz.p.it.ssbd2020.ssbd04.common.AbstractFacade;
import pl.lodz.p.it.ssbd2020.ssbd04.entities.Seat;
import pl.lodz.p.it.ssbd2020.ssbd04.entities.SeatClass;
import pl.lodz.p.it.ssbd2020.ssbd04.entities.Seat_;
import pl.lodz.p.it.ssbd2020.ssbd04.exceptions.AppBaseException;
import pl.lodz.p.it.ssbd2020.ssbd04.exceptions.SeatException;
import pl.lodz.p.it.ssbd2020.ssbd04.interceptors.TrackingInterceptor;
import pl.lodz.p.it.ssbd2020.ssbd04.security.Role;

import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Named;
import javax.interceptor.Interceptors;
import javax.persistence.*;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

/**
 * Klasa definiująca operacje wykonywane na encjach klasy Seat
 * przez zarządcę encji w kontekście trwałości.
 */
@Interceptors({TrackingInterceptor.class})
@Stateless(name = "SeatFacadeMOL")
@Named("SeatFacadeMOL")
@TransactionAttribute(TransactionAttributeType.MANDATORY)
public class SeatFacade extends AbstractFacade<Seat> {

    @PersistenceContext(unitName = "ssbd04molPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public SeatFacade() {
        super(Seat.class);
    }

    /**
     * Zwraca listę zajętych siedzeń dla podanego lotu.
     *
     * @param flightId identyfikator lotu
     * @return liste siedzeń
     * @throws AppBaseException gdy wystąpi problem z bazą danych.
     */
    @PermitAll
    public List<Seat> getTakenSeats(Long flightId) throws AppBaseException {
        try {
            TypedQuery<Seat> seatTypedQuery = em.createNamedQuery("Seat.getTakenSeats", Seat.class);
            seatTypedQuery.setParameter("flightId", flightId);

            return seatTypedQuery.getResultList();
        } catch (NoResultException e) {
            throw SeatException.notFound(e);
        } catch (PersistenceException e) {
            throw AppBaseException.databaseOperation(e);
        }
    }

    /**
     * Wyszukuje siedzenia przypisane do danej klasy miejsc
     *
     * @param seatClass klasa miejsc
     * @return listę siedzeń
     */
    @RolesAllowed(Role.UpdateSeatClass)
    public List<Seat> findBySeatClass(SeatClass seatClass) {
        CriteriaBuilder builder = em.getCriteriaBuilder();
        CriteriaQuery<Seat> query = builder.createQuery(Seat.class);
        Root<Seat> root = query.from(Seat.class);

        query.select(root).where(builder.equal(root.get(Seat_.SEAT_CLASS), seatClass.getId()));

        TypedQuery<Seat> typedQuery = em.createQuery(query);
        return typedQuery.getResultList();
    }

}

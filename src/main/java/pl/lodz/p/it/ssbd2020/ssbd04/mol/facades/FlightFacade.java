package pl.lodz.p.it.ssbd2020.ssbd04.mol.facades;

import org.hibernate.exception.ConstraintViolationException;
import pl.lodz.p.it.ssbd2020.ssbd04.common.AbstractFacade;
import pl.lodz.p.it.ssbd2020.ssbd04.common.Utils;
import pl.lodz.p.it.ssbd2020.ssbd04.entities.*;
import pl.lodz.p.it.ssbd2020.ssbd04.exceptions.AppBaseException;
import pl.lodz.p.it.ssbd2020.ssbd04.exceptions.FlightException;
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
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Klasa definiująca operacje wykonywane na encjach klasy Flight
 * przez zarządcę encji w kontekście trwałości.
 */
@Interceptors({TrackingInterceptor.class})
@Stateless(name = "FlightFacadeMOL")
@Named("FlightFacadeMOL")
@TransactionAttribute(TransactionAttributeType.MANDATORY)
public class FlightFacade extends AbstractFacade<Flight> {

    @PersistenceContext(unitName = "ssbd04molPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public FlightFacade() {
        super(Flight.class);
    }

    @RolesAllowed(Role.CreateFlight)
    @Override
    public void create(Flight entity) throws AppBaseException {
        try {
            super.create(entity);
            em.lock(entity.getAirplaneSchema(), LockModeType.OPTIMISTIC_FORCE_INCREMENT);
        } catch (ConstraintViolationException e) {
            if (e.getCause().getMessage().contains(Flight.CONSTRAINT_FLIGHT_CODE)) {
                throw FlightException.exists();
            }
            throw AppBaseException.databaseOperation(e);
        }
    }

    @Override
    @PermitAll
    public Flight find(Object code) throws AppBaseException {
        return find(code, false);
    }

    /**
     * Szuka encji w bazie danych na podstawie obiektu klucza głównego.
     *
     * @param code            obiekt klucza głównego
     * @param pessimisticLock flaga określająca czy założyć blokadę pesymistyczną
     * @return obiekt lotu
     * @throws AppBaseException gdy operacja nie powiedzie się
     */
    @PermitAll
    public Flight find(Object code, Boolean pessimisticLock) throws AppBaseException {
        try {
            TypedQuery<Flight> flightTypedQuery = em.createNamedQuery("Flight.findByCode", Flight.class);
            if (pessimisticLock) {
                flightTypedQuery.setLockMode(LockModeType.PESSIMISTIC_WRITE);
            }
            flightTypedQuery.setParameter("code", code);
            return flightTypedQuery.getSingleResult();
        } catch (NoResultException e) {
            throw FlightException.notFound();
        } catch (PersistenceException e) {
            throw AppBaseException.databaseOperation(e);
        }
    }

    /**
     * Zwraca wszystkie loty spełniające podane kryteria.
     *
     * @param code           kod lotu
     * @param connection     połączenie
     * @param airplaneSchema schemat samolotu
     * @param from           data, po której zaczyna się lot
     * @param to             data, przed którą zaczyna się lot
     * @param flightStatus   status lotu
     * @return lista lotów spełniających podane kryteria.
     */
    @PermitAll
    public List<Flight> find(String code, Connection connection, AirplaneSchema airplaneSchema, LocalDateTime from,
                             LocalDateTime to, FlightStatus flightStatus) {
        CriteriaBuilder builder = em.getCriteriaBuilder();
        CriteriaQuery<Flight> query = builder.createQuery(Flight.class);
        Root<Flight> root = query.from(Flight.class);
        List<Predicate> predicates = new ArrayList<>();
        if (!Utils.isNullOrEmpty(code)) {
            predicates.add(builder.like(root.get(Flight_.flightCode), code + "%"));
        }
        if (connection != null) {
            predicates.add(builder.equal(root.get(Flight_.connection), connection));
        }
        if (airplaneSchema != null) {
            predicates.add(builder.equal(root.get(Flight_.airplaneSchema), airplaneSchema));
        }
        if (from != null) {
            predicates.add(builder.greaterThanOrEqualTo(root.get(Flight_.startDateTime), from));
        }
        if (to != null) {
            predicates.add(builder.lessThanOrEqualTo(root.get(Flight_.startDateTime), to));
        }
        if (flightStatus != null) {
            predicates.add(builder.equal(root.get(Flight_.status), flightStatus));
        }
        query.where(builder.and(predicates.toArray(new Predicate[0])));
        query.select(root);

        TypedQuery<Flight> typedQuery = em.createQuery(query);
        return typedQuery.getResultList();
    }

    @Override
    @RolesAllowed({Role.UpdateFlight})
    public void edit(Flight entity) throws AppBaseException {
        super.edit(entity);
    }

    /**
     * Zwraca daty z istniejącymi lotami od danej daty
     *
     * @param from data od której wyszukiwane są daty
     * @return daty
     * @throws AppBaseException w przypadku błędu znajdywania dat
     */
    public List<Date> getDates(LocalDateTime from) throws AppBaseException {
        try {
            TypedQuery<Date> flightTypedQuery = em.createNamedQuery("Flight.getDates", Date.class);
            flightTypedQuery.setParameter("from", from);
            return flightTypedQuery.getResultList();
        } catch (PersistenceException e) {
            throw AppBaseException.databaseOperation(e);
        }
    }

    /**
     * Zwraca loty przypisane do danego schematu samolotu
     *
     * @param airplaneSchema schemat samolotu
     * @return lista przypisanych lotów
     * @throws AppBaseException gdy wystapi problem z bazą danych.
     */
    @RolesAllowed(Role.UpdateAirplaneSchema)
    public List<Flight> findByAirplaneSchema(AirplaneSchema airplaneSchema) throws AppBaseException {
        try {
            TypedQuery<Flight> flightTypedQuery = em.createNamedQuery("Flight.findByAirplaneSchema", Flight.class);
            flightTypedQuery.setParameter("schemaId", airplaneSchema.getId());
            return flightTypedQuery.getResultList();
        } catch (PersistenceException e) {
            throw AppBaseException.databaseOperation(e);
        }
    }
}

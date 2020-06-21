package pl.lodz.p.it.ssbd2020.ssbd04.mol.facades;

import org.hibernate.exception.ConstraintViolationException;
import pl.lodz.p.it.ssbd2020.ssbd04.common.AbstractFacade;
import pl.lodz.p.it.ssbd2020.ssbd04.entities.Airport;
import pl.lodz.p.it.ssbd2020.ssbd04.exceptions.AirportException;
import pl.lodz.p.it.ssbd2020.ssbd04.exceptions.AppBaseException;
import pl.lodz.p.it.ssbd2020.ssbd04.interceptors.TrackingInterceptor;
import pl.lodz.p.it.ssbd2020.ssbd04.security.Role;

import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.interceptor.Interceptors;
import javax.persistence.*;
import java.util.List;

@Interceptors({TrackingInterceptor.class})
@Stateless
@TransactionAttribute(TransactionAttributeType.MANDATORY)
public class AirportFacade extends AbstractFacade<Airport> {

    @PersistenceContext(unitName = "ssbd04molPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public AirportFacade() {
        super(Airport.class);
    }

    @RolesAllowed(Role.CreateAirport)
    @Override
    public void create(Airport entity) throws AppBaseException {
        try {
            super.create(entity);
        } catch (ConstraintViolationException e) {
            if (e.getCause().getMessage().contains(Airport.CONSTRAINT_CODE)) {
                throw AirportException.codeNotUnique(entity);
            }

            throw AppBaseException.databaseOperation(e);
        }
    }

    @Override
    @PermitAll
    public List<Airport> findAll() throws AppBaseException {
        return super.findAll();
    }

    /**
     * Zwraca lotnisko o podanym kodzie.
     * @param code kod lotniska
     * @return obiekt lotniska
     * @throws AppBaseException gdy operacja się nie powiedzie
     */
    @Override
    @PermitAll
    public Airport find(Object code) throws AppBaseException {
        try {
            TypedQuery<Airport> airportTypedQuery = em.createNamedQuery("Airport.findByCode", Airport.class);
            airportTypedQuery.setParameter("code", code);

            return airportTypedQuery.getSingleResult();
        } catch (NoResultException e) {
            throw AirportException.notFound();
        } catch (PersistenceException e) {
            throw AppBaseException.databaseOperation(e);
        }
    }

    /**
     * Zwraca lotniska, których kod pasuje do podanej frazy.
     * @param phrase fraza z kodem lotniska
     * @return lista lotnisk
     * @throws AppBaseException gdy operacja się nie powiedzie
     */
    @PermitAll
    public List<Airport> findByMatchingCode(String phrase) throws AppBaseException {
        try {
            TypedQuery<Airport> airportTypedQuery = em.createNamedQuery("Airport.findByMatchingCode", Airport.class);
            airportTypedQuery.setParameter("phrase", phrase == null ? "" : phrase);
            return airportTypedQuery.getResultList();
        } catch (NoResultException e) {
            throw AirportException.notFound();
        } catch (PersistenceException e) {
            throw AppBaseException.databaseOperation(e);
        }
    }

    /**
     * Zwraca wszystkie lotniska spełniające podane kryteria.
     * @param city miasto.
     * @param code kod lotniska.
     * @param country kraj.
     * @param name nazwa lotniska.
     * @return lista lotnisk spełniających podane kryteria.
     * @throws AppBaseException gdy operacja się nie powiedzie
     */
    @PermitAll
    public List<Airport> find(String name, String code, String country, String city) throws AppBaseException {
        try {
            TypedQuery<Airport> airportTypedQuery = em.createNamedQuery("Airport.findByQuery", Airport.class);
            airportTypedQuery.setParameter("code", code == null ? "" : code);
            airportTypedQuery.setParameter("country", country == null ? "" : country);
            airportTypedQuery.setParameter("city", city == null ? "" : city);
            airportTypedQuery.setParameter("name", name == null ? "" : name);

            return airportTypedQuery.getResultList();
        } catch (PersistenceException e) {
            throw AppBaseException.databaseOperation(e);
        }
    }

    /**
     * Zwraca listę nazw krajów w bazie danych.
     * @return lista nazw krajów
     * @throws AppBaseException gdy operacja się nie powiedzie
     */
    @PermitAll
    public List<String> getCountries() throws AppBaseException {
        try {
            TypedQuery<String> airportTypedQuery = em.createNamedQuery("Airport.getUniqueCountries", String.class);

            return airportTypedQuery.getResultList();
        } catch (PersistenceException e) {
            throw AppBaseException.databaseOperation(e);
        }
    }

    /**
     * Zwraca listę nazw miast w bazie danych.
     * @return lista nazw miast
     * @throws AppBaseException gdy operacja się nie powiedzie
     */
    @PermitAll
    public List<String> getCities() throws AppBaseException {
        try {
            TypedQuery<String> airportTypedQuery = em.createNamedQuery("Airport.getUniqueCities", String.class);

            return airportTypedQuery.getResultList();
        } catch (PersistenceException e) {
            throw AppBaseException.databaseOperation(e);
        }
    }

    @Override
    @RolesAllowed({Role.UpdateAirport})
    public void edit(Airport entity) throws AppBaseException {
        try {
            super.edit(entity);
        } catch (ConstraintViolationException e) {
            if (e.getCause().getMessage().contains(Airport.CONSTRAINT_CODE)) {
                throw AirportException.codeNotUnique(entity);
            }

            throw AppBaseException.databaseOperation(e);
        }
    }

    @Override
    @RolesAllowed({Role.DeleteAirport})
    public void remove(Airport entity) throws AppBaseException {
        try {
            super.remove(entity);
        } catch (ConstraintViolationException e) {
            if (e.getCause().getMessage().contains(Airport.CONSTRAINT_DST_IN_USE)
                    || e.getCause().getMessage().contains(Airport.CONSTRAINT_SRC_IN_USE)) {
                throw AirportException.inUse();
            }

            throw AppBaseException.databaseOperation(e);
        }
    }
}

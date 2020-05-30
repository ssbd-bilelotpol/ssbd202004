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
            if (e.getConstraintName().equals(Airport.CONSTRAINT_CODE)) {
                throw AirportException.codeNotUnique(entity);
            }

            throw AppBaseException.databaseOperation(e);
        }
    }

    @Override
    @PermitAll
    public List<Airport> findAll() {
        throw new UnsupportedOperationException();
    }

    @Override
    @PermitAll
    public Airport find(Object id) {
        throw new UnsupportedOperationException();
    }

    /**
     * Zwraca wszystkie lotniska spełniające podane kryteria.
     * @param query kryteria
     * @return lista lotnisk spełniających podane kryteria.
     */
    @PermitAll
    public List<Airport> find(String name, String code, String country, String city) throws AppBaseException {
        try {
            TypedQuery<Airport> airportTypedQuery = em.createNamedQuery("Airport.findByQuery", Airport.class);
            airportTypedQuery.setFlushMode(FlushModeType.COMMIT);
            airportTypedQuery.setParameter("code", code == null ? "" : code);
            airportTypedQuery.setParameter("country", country == null ? "" : country);
            airportTypedQuery.setParameter("city", city == null ? "" : city);
            airportTypedQuery.setParameter("name", name == null ? "" : name);

            return airportTypedQuery.getResultList();
        } catch (NoResultException e) {
            throw AirportException.notFound();
        } catch (PersistenceException e) {
            throw AppBaseException.databaseOperation(e);
        }
    }

    @Override
    @RolesAllowed({Role.UpdateAirport})
    public void edit(Airport entity) throws AppBaseException {
        throw new UnsupportedOperationException();
    }

    @Override
    @RolesAllowed({Role.DeleteAirport})
    public void remove(Airport entity) throws AppBaseException {
        throw new UnsupportedOperationException();
    }
}

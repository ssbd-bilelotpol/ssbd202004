package pl.lodz.p.it.ssbd2020.ssbd04.mob.facades;

import pl.lodz.p.it.ssbd2020.ssbd04.common.AbstractFacade;
import pl.lodz.p.it.ssbd2020.ssbd04.entities.Passenger;
import pl.lodz.p.it.ssbd2020.ssbd04.exceptions.AppBaseException;
import pl.lodz.p.it.ssbd2020.ssbd04.interceptors.TrackingInterceptor;
import pl.lodz.p.it.ssbd2020.ssbd04.security.Role;

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
public class PassengerFacade extends AbstractFacade<Passenger> {

    @PersistenceContext(unitName = "ssbd04mobPU")
    private EntityManager em;

    public PassengerFacade() {
        super(Passenger.class);
    }

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    /**
     * Wyszukuje pasażerów na podstawie przekazanego kryterium.
     * @param flightCode kod lotniska.
     * @param name imię i nazwisko pasażera.
     * @return lotniska spełniające podane kryterium.
     */
    @RolesAllowed(Role.FindClientsByName)
    public List<Passenger> find(String name, String flightCode) throws AppBaseException {
        try {
            TypedQuery<Passenger> airportTypedQuery = em.createNamedQuery("Passenger.findByQuery", Passenger.class);
            airportTypedQuery.setParameter("name", name == null ? "" : name);
            airportTypedQuery.setParameter("flightCode", flightCode == null ? "" : flightCode);

            return airportTypedQuery.getResultList();
        } catch (PersistenceException e) {
            throw AppBaseException.databaseOperation(e);
        }
    }

}

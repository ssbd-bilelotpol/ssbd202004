package pl.lodz.p.it.ssbd2020.ssbd04.mol.facades;

import pl.lodz.p.it.ssbd2020.ssbd04.common.AbstractFacade;
import pl.lodz.p.it.ssbd2020.ssbd04.entities.Flight;
import pl.lodz.p.it.ssbd2020.ssbd04.exceptions.AppBaseException;
import pl.lodz.p.it.ssbd2020.ssbd04.interceptors.TrackingInterceptor;
import pl.lodz.p.it.ssbd2020.ssbd04.mol.dto.FlightQueryDto;
import pl.lodz.p.it.ssbd2020.ssbd04.security.Role;

import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;
import javax.ejb.Stateless;
import javax.interceptor.Interceptors;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;


@Interceptors({TrackingInterceptor.class})
@Stateless
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
        throw new UnsupportedOperationException();
    }

    @Override
    @PermitAll
    public List<Flight> findAll() {
        throw new UnsupportedOperationException();
    }

    @Override
    @PermitAll
    public Flight find(Object id) {
        throw new UnsupportedOperationException();
    }

    /**
     * Zwraca wszystkie loty spełniające podane kryteria.
     * @param query kryteria
     * @return lista lotów spełniających podane kryteria.
     */
    @PermitAll
    public List<Flight> find(FlightQueryDto query) {
        throw new UnsupportedOperationException();
    }

    @Override
    @RolesAllowed({Role.UpdateFlight})
    public void edit(Flight entity) throws AppBaseException {
        throw new UnsupportedOperationException();
    }

    @Override
    @RolesAllowed({Role.CancelFlight})
    public void remove(Flight entity) throws AppBaseException {
        throw new UnsupportedOperationException();
    }
}

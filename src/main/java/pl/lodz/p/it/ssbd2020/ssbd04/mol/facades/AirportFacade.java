package pl.lodz.p.it.ssbd2020.ssbd04.mol.facades;

import pl.lodz.p.it.ssbd2020.ssbd04.common.AbstractFacade;
import pl.lodz.p.it.ssbd2020.ssbd04.entities.Airport;
import pl.lodz.p.it.ssbd2020.ssbd04.exceptions.AppBaseException;
import pl.lodz.p.it.ssbd2020.ssbd04.interceptors.TrackingInterceptor;
import pl.lodz.p.it.ssbd2020.ssbd04.mol.dto.AirportQueryDto;
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
        throw new UnsupportedOperationException();
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
    public List<Airport> find(AirportQueryDto query) {
        throw new UnsupportedOperationException();
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

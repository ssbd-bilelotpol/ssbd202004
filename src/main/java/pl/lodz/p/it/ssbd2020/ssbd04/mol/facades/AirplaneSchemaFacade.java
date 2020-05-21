package pl.lodz.p.it.ssbd2020.ssbd04.mol.facades;

import pl.lodz.p.it.ssbd2020.ssbd04.common.AbstractFacade;
import pl.lodz.p.it.ssbd2020.ssbd04.entities.AirplaneSchema;
import pl.lodz.p.it.ssbd2020.ssbd04.exceptions.AppBaseException;
import pl.lodz.p.it.ssbd2020.ssbd04.interceptors.TrackingInterceptor;
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
public class AirplaneSchemaFacade extends AbstractFacade<AirplaneSchema> {

    @PersistenceContext(unitName = "ssbd04molPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public AirplaneSchemaFacade() {
        super(AirplaneSchema.class);
    }

    @RolesAllowed(Role.CreateAirplaneSchema)
    @Override
    public void create(AirplaneSchema entity) throws AppBaseException {
        throw new UnsupportedOperationException();
    }

    @Override
    @RolesAllowed(Role.GetAllAirplaneSchemas)
    public List<AirplaneSchema> findAll() throws AppBaseException {
        throw new UnsupportedOperationException();
    }

    @Override
    @PermitAll
    public AirplaneSchema find(Object id) {
        throw new UnsupportedOperationException();
    }

    @Override
    @RolesAllowed({Role.UpdateAirplaneSchema})
    public void edit(AirplaneSchema entity) throws AppBaseException {
        throw new UnsupportedOperationException();
    }

    @Override
    @RolesAllowed({Role.DeleteAirplaneSchema})
    public void remove(AirplaneSchema entity) throws AppBaseException {
        // throws: AirplaneSchemaInUse (is attached to a flight)
        throw new UnsupportedOperationException();
    }
}

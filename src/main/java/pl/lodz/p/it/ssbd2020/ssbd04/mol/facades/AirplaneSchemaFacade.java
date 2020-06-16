package pl.lodz.p.it.ssbd2020.ssbd04.mol.facades;

import pl.lodz.p.it.ssbd2020.ssbd04.common.AbstractFacade;
import pl.lodz.p.it.ssbd2020.ssbd04.entities.AirplaneSchema;
import pl.lodz.p.it.ssbd2020.ssbd04.exceptions.AppBaseException;
import pl.lodz.p.it.ssbd2020.ssbd04.interceptors.TrackingInterceptor;
import pl.lodz.p.it.ssbd2020.ssbd04.security.Role;

import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.interceptor.Interceptors;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;
import javax.persistence.TypedQuery;
import java.util.List;


@Interceptors({TrackingInterceptor.class})
@Stateless
@TransactionAttribute(TransactionAttributeType.MANDATORY)
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
        super.create(entity);
    }

    @Override
    @RolesAllowed(Role.GetAllAirplaneSchemas)
    public List<AirplaneSchema> findAll() throws AppBaseException {
        throw new UnsupportedOperationException();
    }

    @Override
    @PermitAll
    public AirplaneSchema find(Object id) throws AppBaseException {
        return super.find(id);
    }

    @Override
    @RolesAllowed({Role.UpdateAirplaneSchema})
    public void edit(AirplaneSchema entity) throws AppBaseException {
        super.edit(entity);
    }

    @Override
    @RolesAllowed({Role.DeleteAirplaneSchema})
    public void remove(AirplaneSchema entity) throws AppBaseException {
        // throws: AirplaneSchemaInUse (is attached to a flight)
        throw new UnsupportedOperationException();
    }

    /**
     * Szuka schematów samolotów których nazwa pasuje do podanej nazwy.
     * @param name podana nazwa
     * @return listę schematów samolotów
     */
    @RolesAllowed({Role.GetAllAirplaneSchemas})
    public List<AirplaneSchema> findByName(String name) throws AppBaseException {
        try {
            TypedQuery<AirplaneSchema> query = em.createNamedQuery("AirplaneSchema.findByName", AirplaneSchema.class);
            query.setParameter("name", name == null ? "" : name);
            return query.getResultList();
        } catch (PersistenceException e) {
            throw AppBaseException.databaseOperation(e);
        }
    }
}

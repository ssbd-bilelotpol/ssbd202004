package pl.lodz.p.it.ssbd2020.ssbd04.mol.facades;

import org.hibernate.exception.ConstraintViolationException;
import pl.lodz.p.it.ssbd2020.ssbd04.common.AbstractFacade;
import pl.lodz.p.it.ssbd2020.ssbd04.entities.AirplaneSchema;
import pl.lodz.p.it.ssbd2020.ssbd04.exceptions.AirplaneSchemaException;
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

/**
 * Klasa definiująca operacje wykonywane na encjach klasy AirplaneSchema
 * przez zarządcę encji w kontekście trwałości.
 */
@Interceptors({TrackingInterceptor.class})
@Stateless(name = "AirplaneSchemaFacadeMOL")
@Named("AirplaneSchemaFacadeMOL")
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
        try {
            super.create(entity);
        } catch (ConstraintViolationException e) {
            if (e.getCause().getMessage().contains(AirplaneSchema.CONSTRAINT_SCHEMA_NAME)) {
                throw AirplaneSchemaException.nameTaken();
            }

            throw e;
        }

    }

    @Override
    @PermitAll
    public AirplaneSchema find(Object id) throws AppBaseException {
        return super.find(id);
    }

    @Override
    @RolesAllowed({Role.UpdateAirplaneSchema})
    public void edit(AirplaneSchema entity) throws AppBaseException {
        try {
            super.edit(entity);
        } catch (ConstraintViolationException e) {
            if (e.getCause().getMessage().contains(AirplaneSchema.CONSTRAINT_SCHEMA_NAME)) {
                throw AirplaneSchemaException.nameTaken();
            }

            throw e;
        }
    }

    @Override
    @RolesAllowed({Role.DeleteAirplaneSchema})
    public void remove(AirplaneSchema airplaneSchema) throws AppBaseException {
        try {
            super.remove(airplaneSchema);
        } catch (ConstraintViolationException e) {
            if (e.getCause().getMessage().contains(AirplaneSchema.CONSTRAINT_IN_USE) || e.getCause().getMessage().contains(AirplaneSchema.CONSTRAINT_SEAT_USE)) {
                throw AirplaneSchemaException.inUse(airplaneSchema);
            }

            throw AppBaseException.databaseOperation(e);
        }
    }

    /**
     * Szuka schematów samolotów których nazwa pasuje do podanej nazwy.
     *
     * @param name podana nazwa
     * @return listę schematów samolotów
     * @throws AppBaseException gdy operacja nie powiedzie się
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

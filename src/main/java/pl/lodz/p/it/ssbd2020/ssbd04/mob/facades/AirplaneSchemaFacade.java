package pl.lodz.p.it.ssbd2020.ssbd04.mob.facades;

import pl.lodz.p.it.ssbd2020.ssbd04.common.AbstractFacade;
import pl.lodz.p.it.ssbd2020.ssbd04.entities.AirplaneSchema;
import pl.lodz.p.it.ssbd2020.ssbd04.exceptions.AppBaseException;
import pl.lodz.p.it.ssbd2020.ssbd04.interceptors.TrackingInterceptor;

import javax.annotation.security.PermitAll;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.interceptor.Interceptors;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 * Klasa definiująca operacje wykonywane na encjach klasy AirplaneSchema
 * przez zarządcę encji w kontekście trwałości
 */
@Interceptors({TrackingInterceptor.class})
@Stateless(name = "AirplaneSchemaFacadeMOB")
@Named("AirplaneSchemaFacadeMOB")
@TransactionAttribute(TransactionAttributeType.MANDATORY)
public class AirplaneSchemaFacade extends AbstractFacade<AirplaneSchema> {

    @PersistenceContext(unitName = "ssbd04mobPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public AirplaneSchemaFacade() {
        super(AirplaneSchema.class);
    }

    @Override
    @PermitAll
    public AirplaneSchema find(Object id) throws AppBaseException {
        return super.find(id);
    }
}

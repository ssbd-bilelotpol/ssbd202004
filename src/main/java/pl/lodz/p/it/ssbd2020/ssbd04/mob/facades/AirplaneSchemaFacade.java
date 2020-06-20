package pl.lodz.p.it.ssbd2020.ssbd04.mob.facades;

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


@Interceptors({TrackingInterceptor.class})
@Stateless
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

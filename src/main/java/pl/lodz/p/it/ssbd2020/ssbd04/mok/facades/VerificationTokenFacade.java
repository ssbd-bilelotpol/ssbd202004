package pl.lodz.p.it.ssbd2020.ssbd04.mok.facades;

import pl.lodz.p.it.ssbd2020.ssbd04.common.AbstractFacade;
import pl.lodz.p.it.ssbd2020.ssbd04.entities.verification_tokens.VerificationToken;
import pl.lodz.p.it.ssbd2020.ssbd04.exceptions.AppBaseException;
import pl.lodz.p.it.ssbd2020.ssbd04.interceptors.TrackingInterceptor;

import javax.annotation.security.PermitAll;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.interceptor.Interceptors;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;
import javax.persistence.TypedQuery;
import java.time.LocalDateTime;
import java.util.List;


@Interceptors({TrackingInterceptor.class})
@Stateless
@TransactionAttribute(TransactionAttributeType.MANDATORY)
public class VerificationTokenFacade extends AbstractFacade<VerificationToken> {

    @PersistenceContext(unitName = "ssbd04mokPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public VerificationTokenFacade() {
        super(VerificationToken.class);
    }

    @Override
    @PermitAll
    public VerificationToken find(Object id) throws AppBaseException {
        return super.find(id);
    }

    @Override
    @PermitAll
    public void create(VerificationToken entity) throws AppBaseException {
        super.create(entity);
    }

    @Override
    @PermitAll
    public void remove(VerificationToken entity) throws AppBaseException {
        super.remove(entity);
    }

    /**
     * Zwraca żetony, których data wygaśnięcia poprzedza daną datę.
     *
     * @param dateTime
     * @return
     * @throws AppBaseException
     */
    @PermitAll
    public List<VerificationToken> findExpiredBefore(LocalDateTime dateTime) throws AppBaseException {
        try {
            TypedQuery<VerificationToken> query = em.createNamedQuery("VerificationToken.findExpiredBefore", VerificationToken.class);
            query.setParameter("dateTime", dateTime);
            return query.getResultList();
        } catch (PersistenceException e) {
            throw AppBaseException.databaseOperation(e);
        }
    }
}

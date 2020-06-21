package pl.lodz.p.it.ssbd2020.ssbd04.mol.facades;

import pl.lodz.p.it.ssbd2020.ssbd04.common.AbstractFacade;
import pl.lodz.p.it.ssbd2020.ssbd04.entities.Benefit;
import pl.lodz.p.it.ssbd2020.ssbd04.exceptions.AppBaseException;
import pl.lodz.p.it.ssbd2020.ssbd04.exceptions.SeatClassException;
import pl.lodz.p.it.ssbd2020.ssbd04.interceptors.TrackingInterceptor;
import pl.lodz.p.it.ssbd2020.ssbd04.security.Role;

import javax.annotation.security.DenyAll;
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
@DenyAll
@TransactionAttribute(TransactionAttributeType.MANDATORY)
public class BenefitFacade extends AbstractFacade<Benefit> {

    @PersistenceContext(unitName = "ssbd04molPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public BenefitFacade() {
        super(Benefit.class);
    }

    @Override
    @RolesAllowed(Role.GetAllBenefits)
    public List<Benefit> findAll() throws AppBaseException {
        return super.findAll();
    }

    /**
     * Wyszukuje dodatek na podstawie nazwy
     * @param name nazwa dodatku
     * @return lista dodatków
     * @throws AppBaseException gdy wystąpi problem z bazą danych, bądź dodatek nie istnieje.
     */
    @PermitAll
    public Benefit findByName(String name) throws AppBaseException {
        try {
            TypedQuery<Benefit> benefitTypedQuery = em.createNamedQuery("Benefit.findByName", Benefit.class);
            benefitTypedQuery.setParameter("name", name);
            return benefitTypedQuery.getSingleResult();
        } catch (NoResultException e) {
            throw SeatClassException.notFound(e);
        } catch (PersistenceException e) {
            throw AppBaseException.databaseOperation(e);
        }
    }
}

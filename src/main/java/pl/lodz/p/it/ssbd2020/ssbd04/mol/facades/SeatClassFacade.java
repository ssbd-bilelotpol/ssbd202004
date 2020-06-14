package pl.lodz.p.it.ssbd2020.ssbd04.mol.facades;

import org.hibernate.exception.ConstraintViolationException;
import pl.lodz.p.it.ssbd2020.ssbd04.common.AbstractFacade;
import pl.lodz.p.it.ssbd2020.ssbd04.entities.Benefit;
import pl.lodz.p.it.ssbd2020.ssbd04.entities.SeatClass;
import pl.lodz.p.it.ssbd2020.ssbd04.exceptions.AppBaseException;
import pl.lodz.p.it.ssbd2020.ssbd04.exceptions.SeatClassException;
import pl.lodz.p.it.ssbd2020.ssbd04.interceptors.TrackingInterceptor;
import pl.lodz.p.it.ssbd2020.ssbd04.security.Role;

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
@TransactionAttribute(TransactionAttributeType.MANDATORY)
public class SeatClassFacade extends AbstractFacade<SeatClass> {

    @PersistenceContext(unitName = "ssbd04molPU")
    private EntityManager em;

    public SeatClassFacade() {
        super(SeatClass.class);
    }

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    @PermitAll
    public SeatClass findByName(String name) throws AppBaseException {
        try {
            TypedQuery<SeatClass> seatClassTypedQuery = em.createNamedQuery("SeatClass.findByName", SeatClass.class);
            seatClassTypedQuery.setParameter("name", name);
            return seatClassTypedQuery.getSingleResult();
        } catch (NoResultException e) {
            throw SeatClassException.notFound(e);
        } catch (PersistenceException e) {
            throw AppBaseException.databaseOperation(e);
        }
    }

    @Override
    @PermitAll
    public List<SeatClass> findAll() throws AppBaseException {
        return super.findAll();
    }

    @Override
    @RolesAllowed(Role.CreateSeatClass)
    public void create(SeatClass entity) throws AppBaseException {
        try {
            super.create(entity);
        } catch (ConstraintViolationException e) {
            if (e.getConstraintName().equals(SeatClass.CONSTRAINT_NAME)) {
                throw SeatClassException.nameTaken(entity);
            } else if (e.getConstraintName().equals(Benefit.CONSTRAINT_NAME)) {
                throw SeatClassException.benefitExists(entity);
            }
            throw AppBaseException.databaseOperation(e);
        }
    }

    @Override
    @RolesAllowed(Role.DeleteSeatClass)
    public void remove(SeatClass entity) throws AppBaseException {
        // throws: SeatClassInUse
        throw new UnsupportedOperationException();
    }

    @Override
    @RolesAllowed(Role.UpdateSeatClass)
    public void edit(SeatClass entity) throws AppBaseException {
        try {
            super.edit(entity);
        } catch (ConstraintViolationException e) {
            if (e.getConstraintName().equals(Benefit.CONSTRAINT_NAME)) {
                throw SeatClassException.benefitExists(entity);
            }
            throw AppBaseException.databaseOperation(e);
        }
    }
}

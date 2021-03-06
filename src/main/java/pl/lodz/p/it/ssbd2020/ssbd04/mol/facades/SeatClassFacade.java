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

/**
 * Klasa definiująca operacje wykonywane na encjach klasy SeatClass
 * przez zarządcę encji w kontekście trwałości.
 */
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

    /**
     * Wyszukuje klasę miejsc na podstawie nazwy
     *
     * @param name nazwa klasy miejsc
     * @return obiekt klasy miejsc
     * @throws AppBaseException gdy klasa miejsc nie została znaleziona, bądź wystąpił problem z bazą danych.
     */
    @PermitAll
    public SeatClass findByName(String name) throws AppBaseException {
        try {
            TypedQuery<SeatClass> seatClassTypedQuery = em.createNamedQuery("SeatClass.findByName", SeatClass.class);
            seatClassTypedQuery.setParameter("name", name);
            seatClassTypedQuery.setLockMode(LockModeType.PESSIMISTIC_WRITE);
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
        List<SeatClass> seatClasses = super.findAll();
        seatClasses
                .forEach(sc -> em.lock(sc, LockModeType.PESSIMISTIC_READ));
        return seatClasses;
    }

    @Override
    @RolesAllowed(Role.CreateSeatClass)
    public void create(SeatClass entity) throws AppBaseException {
        try {
            super.create(entity);
        } catch (ConstraintViolationException e) {
            if (e.getCause().getMessage().contains(SeatClass.CONSTRAINT_NAME)) {
                throw SeatClassException.nameTaken(entity);
            } else if (e.getCause().getMessage().contains(Benefit.CONSTRAINT_NAME)) {
                throw SeatClassException.benefitExists(entity);
            }
            throw AppBaseException.databaseOperation(e);
        }
    }

    @Override
    @RolesAllowed(Role.DeleteSeatClass)
    public void remove(SeatClass entity) throws AppBaseException {
        try {
            super.remove(entity);
        } catch (ConstraintViolationException e) {
            if (e.getCause().getMessage().contains(SeatClass.CONSTRAINT_SEAT_CLASS_IN_USE)) {
                throw SeatClassException.inUse(entity);
            }
            throw AppBaseException.databaseOperation(e);
        }
    }


    @Override
    @RolesAllowed(Role.UpdateSeatClass)
    public void edit(SeatClass entity) throws AppBaseException {
        try {
            super.edit(entity);
        } catch (ConstraintViolationException e) {
            if (e.getCause().getMessage().contains(Benefit.CONSTRAINT_NAME)) {
                throw SeatClassException.benefitExists(entity);
            }
            throw AppBaseException.databaseOperation(e);
        }
    }
}

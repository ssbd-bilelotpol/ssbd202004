package pl.lodz.p.it.ssbd2020.ssbd04.mol.facades;

import pl.lodz.p.it.ssbd2020.ssbd04.common.AbstractFacade;
import pl.lodz.p.it.ssbd2020.ssbd04.entities.*;
import pl.lodz.p.it.ssbd2020.ssbd04.exceptions.AppBaseException;
import pl.lodz.p.it.ssbd2020.ssbd04.exceptions.SeatException;
import pl.lodz.p.it.ssbd2020.ssbd04.interceptors.TrackingInterceptor;
import pl.lodz.p.it.ssbd2020.ssbd04.security.Role;

import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.interceptor.Interceptors;
import javax.persistence.*;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.ParameterExpression;
import javax.persistence.criteria.Root;
import java.util.List;

import static pl.lodz.p.it.ssbd2020.ssbd04.security.Role.GetTakenSeats;

@Interceptors({TrackingInterceptor.class})
@Stateless
@TransactionAttribute(TransactionAttributeType.MANDATORY)
public class SeatFacade extends AbstractFacade<Seat> {

    @PersistenceContext(unitName = "ssbd04molPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public SeatFacade() {
        super(Seat.class);
    }

    @PermitAll
    @RolesAllowed(Role.GetTakenSeats)
    public List<Seat> getTakenSeats(Long flightId) throws AppBaseException {
        try {
            TypedQuery<Seat> seatTypedQuery = em.createNamedQuery("Seat.getTakenSeats", Seat.class);
            seatTypedQuery.setParameter("flightId", flightId);

            return seatTypedQuery.getResultList();
        } catch (NoResultException e) {
            throw SeatException.notFound(e);
        } catch (PersistenceException e) {
            throw AppBaseException.databaseOperation(e);
        }
    }

    @RolesAllowed(Role.UpdateSeatClass)
    public List<Seat> findBySeatClass(SeatClass seatClass) throws AppBaseException {
        CriteriaBuilder builder = em.getCriteriaBuilder();
        CriteriaQuery<Seat> query = builder.createQuery(Seat.class);
        Root<Seat> root = query.from(Seat.class);

        query.select(root).where(builder.equal(root.get(Seat_.SEAT_CLASS), seatClass.getId()));

        TypedQuery<Seat> typedQuery = em.createQuery(query);
        return typedQuery.getResultList();
    }

}

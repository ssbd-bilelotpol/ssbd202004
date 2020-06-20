package pl.lodz.p.it.ssbd2020.ssbd04.mob.facades;

import pl.lodz.p.it.ssbd2020.ssbd04.common.AbstractFacade;
import pl.lodz.p.it.ssbd2020.ssbd04.common.Utils;
import pl.lodz.p.it.ssbd2020.ssbd04.entities.AirplaneSchema;
import pl.lodz.p.it.ssbd2020.ssbd04.entities.Connection;
import pl.lodz.p.it.ssbd2020.ssbd04.entities.Flight;
import pl.lodz.p.it.ssbd2020.ssbd04.entities.Flight_;
import pl.lodz.p.it.ssbd2020.ssbd04.exceptions.AppBaseException;
import pl.lodz.p.it.ssbd2020.ssbd04.exceptions.FlightException;
import pl.lodz.p.it.ssbd2020.ssbd04.interceptors.TrackingInterceptor;

import javax.annotation.security.PermitAll;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.interceptor.Interceptors;
import javax.persistence.*;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


@Interceptors({TrackingInterceptor.class})
@Stateless
@TransactionAttribute(TransactionAttributeType.MANDATORY)
public class FlightFacade extends AbstractFacade<Flight> {

    @PersistenceContext(unitName = "ssbd04mobPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public FlightFacade() {
        super(Flight.class);
    }

    @Override
    @PermitAll
    public Flight find(Object code) throws AppBaseException {
        try {
            TypedQuery<Flight> flightTypedQuery = em.createNamedQuery("Flight.findByCode", Flight.class);
            flightTypedQuery.setLockMode(LockModeType.PESSIMISTIC_READ);
            flightTypedQuery.setParameter("code", code);
            return flightTypedQuery.getSingleResult();
        } catch (NoResultException e) {
            throw FlightException.notFound();
        } catch (PersistenceException e) {
            throw AppBaseException.databaseOperation(e);
        }
    }

    /**
     * Zwraca wszystkie loty spełniające podane kryteria.
     * @param code kod lotu
     * @param connection połączenie
     * @param airplaneSchema schemat samolotu
     * @param from data, po której zaczyna się lot
     * @param to data, przed którą zaczyna się lot
     * @return lista lotów spełniających podane kryteria.
     */
    @PermitAll
    public List<Flight> find(String code, Connection connection, AirplaneSchema airplaneSchema) {
        CriteriaBuilder builder = em.getCriteriaBuilder();
        CriteriaQuery<Flight> query = builder.createQuery(Flight.class);
        Root<Flight> root = query.from(Flight.class);
        List<Predicate> predicates = new ArrayList<Predicate>();
        if(!Utils.isNullOrEmpty(code)) {
            predicates.add(builder.like(root.get(Flight_.flightCode), code+"%"));
        }
        if(connection != null) {
            predicates.add(builder.equal(root.get(Flight_.connection), connection));
        }
        if(airplaneSchema != null) {
            predicates.add(builder.equal(root.get(Flight_.airplaneSchema), airplaneSchema));
        }
        query.where(builder.and(predicates.toArray(new Predicate[0])));
        query.select(root);

        TypedQuery<Flight> typedQuery = em.createQuery(query);
        return typedQuery.getResultList();
    }

}

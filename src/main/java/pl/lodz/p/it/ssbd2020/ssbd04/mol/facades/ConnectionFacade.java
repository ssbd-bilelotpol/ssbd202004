package pl.lodz.p.it.ssbd2020.ssbd04.mol.facades;

import org.hibernate.exception.ConstraintViolationException;
import pl.lodz.p.it.ssbd2020.ssbd04.common.AbstractFacade;
import pl.lodz.p.it.ssbd2020.ssbd04.entities.Airport;
import pl.lodz.p.it.ssbd2020.ssbd04.entities.Connection;
import pl.lodz.p.it.ssbd2020.ssbd04.exceptions.AirportException;
import pl.lodz.p.it.ssbd2020.ssbd04.exceptions.AppBaseException;
import pl.lodz.p.it.ssbd2020.ssbd04.exceptions.ConnectionException;
import pl.lodz.p.it.ssbd2020.ssbd04.interceptors.TrackingInterceptor;
import pl.lodz.p.it.ssbd2020.ssbd04.mol.dto.ConnectionDto;
import pl.lodz.p.it.ssbd2020.ssbd04.mol.dto.ConnectionQueryDto;
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
public class ConnectionFacade extends AbstractFacade<Connection> {

    @PersistenceContext(unitName = "ssbd04molPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public ConnectionFacade() {
        super(Connection.class);
    }

    @RolesAllowed(Role.CreateConnection)
    @Override
    public void create(Connection entity) throws AppBaseException {
        try {
            super.create(entity);
        } catch (ConstraintViolationException e) {

            throw AppBaseException.databaseOperation(e);
        }
    }

    @Override
    @PermitAll
    public List<Connection> findAll() {
        throw new UnsupportedOperationException();
    }

    @Override
    @PermitAll
    public Connection find(Object id) {
        throw new UnsupportedOperationException();
    }

    @PermitAll
    public Connection find(Airport destination, Airport source) throws AppBaseException {
        try {
            TypedQuery<Connection> connectionTypedQuery = em.createNamedQuery("Connection.findBetween", Connection.class);
            connectionTypedQuery.setParameter("destinationId", destination == null ? "" : destination.getId());
            connectionTypedQuery.setParameter("sourceId", source == null ? "" : source.getId());

            return connectionTypedQuery.getSingleResult();
        } catch(NoResultException e) {
            throw ConnectionException.notFound();
        } catch (PersistenceException e) {
            throw AppBaseException.databaseOperation(e);
        }
    }

    /**
     * Zwraca wszystkie połączenia spełniające podane kryteria.
     * @param query kryteria
     * @return lista połączeń spełniających podane kryteria.
     */
    @PermitAll
    public List<Connection> find(ConnectionQueryDto query) {
        throw new UnsupportedOperationException();
    }

    @Override
    @RolesAllowed({Role.UpdateConnection, Role.CalculateConnectionProfit})
    public void edit(Connection entity) throws AppBaseException {
        throw new UnsupportedOperationException();
    }

    @Override
    @RolesAllowed({Role.DeleteConnection})
    public void remove(Connection entity) throws AppBaseException {
        throw new UnsupportedOperationException();
    }
}

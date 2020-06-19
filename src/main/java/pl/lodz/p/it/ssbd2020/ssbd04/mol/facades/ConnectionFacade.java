package pl.lodz.p.it.ssbd2020.ssbd04.mol.facades;

import org.hibernate.exception.ConstraintViolationException;
import pl.lodz.p.it.ssbd2020.ssbd04.common.AbstractFacade;
import pl.lodz.p.it.ssbd2020.ssbd04.entities.Connection;
import pl.lodz.p.it.ssbd2020.ssbd04.exceptions.AppBaseException;
import pl.lodz.p.it.ssbd2020.ssbd04.exceptions.ConnectionException;
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

import static pl.lodz.p.it.ssbd2020.ssbd04.entities.Connection.CONNECTION_NOT_UNIQUE;

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

    /**
     * Zapisuje w bazie połączenie.
     * @param entity nowe połączenie
     * @throws AppBaseException gdy połączenie już istnieje
     */
    @RolesAllowed(Role.CreateConnection)
    @Override
    public void create(Connection entity) throws AppBaseException {
        try {
            super.create(entity);
        } catch (ConstraintViolationException e) {
            if (e.getCause().getMessage().contains(Connection.CONNECTION_NOT_UNIQUE)) {
                throw ConnectionException.notUnique();
            }
            throw AppBaseException.databaseOperation(e);
        }
    }

    @Override
    @PermitAll
    public List<Connection> findAll() throws AppBaseException {
        return super.findAll();
    }

    @Override
    @PermitAll
    public Connection find(Object id) throws AppBaseException {
        return super.find(id);
    }

    /**
     * Wszykuje połączenie na podstawie frazy.
     * @param phrase fraza do szukania(np. WSZ - LDZ)
     * @return znalezione połączenia
     * @throws AppBaseException w przypadku niepowodzenia operacji
     */
    @PermitAll
    public List<Connection> findByPhrase(String phrase) throws AppBaseException {
        try {
            TypedQuery<Connection> connectionTypedQuery = em.createNamedQuery("Connection.findByPhrase", Connection.class);
            connectionTypedQuery.setParameter("phrase", phrase == null ? "" : phrase);
            return connectionTypedQuery.getResultList();
        } catch (PersistenceException e) {
            throw AppBaseException.databaseOperation(e);
        }
    }

    /**
     * Wyszukuje połączenia na podstawie ID lotnisk przylotu oraz wylotu.
     * @param destinationCode lotnisko przylotu
     * @param sourceCode lotnisko wylotu
     * @return znaleziona relacja
     * @throws AppBaseException gdy nie znaleziono połączenia
     */
    @PermitAll
    public List<Connection> find(String destinationCode, String sourceCode) throws AppBaseException {
        try {
            TypedQuery<Connection> connectionTypedQuery = em.createNamedQuery("Connection.findBetween", Connection.class);
            connectionTypedQuery.setParameter("destinationCode", destinationCode == null ? "" : destinationCode);
            connectionTypedQuery.setParameter("sourceCode", sourceCode == null ? "" : sourceCode);
            return connectionTypedQuery.getResultList();
        } catch (PersistenceException e) {
            throw AppBaseException.databaseOperation(e);
        }
    }

    /**
     * Wyszukuje połączenia na podstawie ID lotniska przylotu.
     * @param destinationCode lotnisko przylotu
     * @return znaleziona relacja
     * @throws AppBaseException gdy nie znaleziono połączenia
     */
    @PermitAll
    public List<Connection> findByDestination(String destinationCode) throws AppBaseException {
        try {
            TypedQuery<Connection> connectionTypedQuery = em.createNamedQuery("Connection.findByDestination", Connection.class);
            connectionTypedQuery.setParameter("destinationCode", destinationCode == null ? "" : destinationCode);
            return connectionTypedQuery.getResultList();
        } catch (PersistenceException e) {
            throw AppBaseException.databaseOperation(e);
        }
    }

    /**
     * Wyszukuje połączenia na podstawie ID lotniska wylotu.
     * @param sourceCode lotnisko wylotu
     * @return znaleziona relacja
     * @throws AppBaseException gdy nie znaleziono połączenia
     */
    @PermitAll
    public List<Connection> findBySource(String sourceCode) throws AppBaseException {
        try {
            TypedQuery<Connection> connectionTypedQuery = em.createNamedQuery("Connection.findBySource", Connection.class);
            connectionTypedQuery.setParameter("sourceCode", sourceCode == null ? "" : sourceCode);
            return connectionTypedQuery.getResultList();
        } catch (PersistenceException e) {
            throw AppBaseException.databaseOperation(e);
        }
    }

    @Override
    @RolesAllowed({Role.UpdateConnection, Role.CalculateConnectionProfit})
    public void edit(Connection entity) throws AppBaseException {
         super.edit(entity);
    }

    @Override
    @RolesAllowed({Role.DeleteConnection})
    public void remove(Connection entity) throws AppBaseException {
        try {
            super.remove(entity);
        } catch (ConstraintViolationException e) {
            if (e.getCause().getMessage().contains(Connection.CONSTRAINT_FLIGHT_CONNECTION_FK)) {
                throw ConnectionException.inUse(entity);
            }

            throw AppBaseException.databaseOperation(e);
        }
    }
}

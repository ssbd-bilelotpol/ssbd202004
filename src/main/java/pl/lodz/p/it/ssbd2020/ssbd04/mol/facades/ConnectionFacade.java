package pl.lodz.p.it.ssbd2020.ssbd04.mol.facades;

import pl.lodz.p.it.ssbd2020.ssbd04.common.AbstractFacade;
import pl.lodz.p.it.ssbd2020.ssbd04.entities.Airport;
import pl.lodz.p.it.ssbd2020.ssbd04.entities.Connection;
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
        super.create(entity);
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
            connectionTypedQuery.setParameter("phrase", phrase);
            return connectionTypedQuery.getResultList();
        } catch (PersistenceException e) {
            throw AppBaseException.databaseOperation(e);
        }
    }

    /**
     * Wyszukuje połączenia na podstawie ID lotnisk przylotu oraz wylotu.
     * @param destination lotnisko przylotu
     * @param source lotnisko wylotu
     * @return znaleziona relacja
     * @throws AppBaseException gdy nie znaleziono połączenia
     */
    @PermitAll
    public List<Connection> find(Airport destination, Airport source) throws AppBaseException {
        try {
            TypedQuery<Connection> connectionTypedQuery = em.createNamedQuery("Connection.findBetween", Connection.class);
            connectionTypedQuery.setParameter("destinationId", destination.getId());
            connectionTypedQuery.setParameter("sourceId", source.getId());
            return connectionTypedQuery.getResultList();
        } catch (PersistenceException e) {
            throw AppBaseException.databaseOperation(e);
        }
    }

    /**
     * Wyszukuje połączenia na podstawie ID lotniska przylotu.
     * @param destination lotnisko przylotu
     * @return znaleziona relacja
     * @throws AppBaseException gdy nie znaleziono połączenia
     */
    @PermitAll
    public List<Connection> findByDestination(Airport destination) throws AppBaseException {
        try {
            TypedQuery<Connection> connectionTypedQuery = em.createNamedQuery("Connection.findByDestination", Connection.class);
            connectionTypedQuery.setParameter("destinationId", destination.getId());
            return connectionTypedQuery.getResultList();
        } catch (PersistenceException e) {
            throw AppBaseException.databaseOperation(e);
        }
    }

    /**
     * Wyszukuje połączenia na podstawie ID lotniska wylotu.
     * @param source lotnisko wylotu
     * @return znaleziona relacja
     * @throws AppBaseException gdy nie znaleziono połączenia
     */
    @PermitAll
    public List<Connection> findBySource(Airport source) throws AppBaseException {
        try {
            TypedQuery<Connection> connectionTypedQuery = em.createNamedQuery("Connection.findBySource", Connection.class);
            connectionTypedQuery.setParameter("sourceId", source.getId());
            return connectionTypedQuery.getResultList();
        } catch (PersistenceException e) {
            throw AppBaseException.databaseOperation(e);
        }
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

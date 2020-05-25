package pl.lodz.p.it.ssbd2020.ssbd04.mol.facades;

import pl.lodz.p.it.ssbd2020.ssbd04.common.AbstractFacade;
import pl.lodz.p.it.ssbd2020.ssbd04.entities.Connection;
import pl.lodz.p.it.ssbd2020.ssbd04.exceptions.AppBaseException;
import pl.lodz.p.it.ssbd2020.ssbd04.interceptors.TrackingInterceptor;
import pl.lodz.p.it.ssbd2020.ssbd04.mol.dto.ConnectionQueryDto;
import pl.lodz.p.it.ssbd2020.ssbd04.security.Role;

import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.interceptor.Interceptors;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
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
        throw new UnsupportedOperationException();
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

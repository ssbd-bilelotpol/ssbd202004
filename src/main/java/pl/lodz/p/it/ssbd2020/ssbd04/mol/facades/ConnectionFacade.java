package pl.lodz.p.it.ssbd2020.ssbd04.mol.facades;

import pl.lodz.p.it.ssbd2020.ssbd04.common.AbstractFacade;
import pl.lodz.p.it.ssbd2020.ssbd04.entities.Connection;
import pl.lodz.p.it.ssbd2020.ssbd04.interceptors.TrackingInterceptor;

import javax.ejb.Stateless;
import javax.interceptor.Interceptors;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;


@Interceptors({TrackingInterceptor.class})
@Stateless
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

}

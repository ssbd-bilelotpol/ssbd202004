package pl.lodz.p.it.ssbd2020.ssbd04.mob.services;

import pl.lodz.p.it.ssbd2020.ssbd04.entities.Connection;
import pl.lodz.p.it.ssbd2020.ssbd04.exceptions.AppBaseException;
import pl.lodz.p.it.ssbd2020.ssbd04.interceptors.TrackingInterceptor;
import pl.lodz.p.it.ssbd2020.ssbd04.mob.facades.ConnectionFacade;
import pl.lodz.p.it.ssbd2020.ssbd04.security.Role;

import javax.annotation.security.RolesAllowed;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;
import javax.interceptor.Interceptors;
import java.util.List;

/**
 * Przetwarzanie logiki biznesowej połączeń.
 */
@Interceptors({TrackingInterceptor.class})
@Stateless
@TransactionAttribute(TransactionAttributeType.MANDATORY)
public class ConnectionService {

    @Inject
    private ConnectionFacade connectionFacade;

    /**
     * Zwraca listę z informacjami o zyskach na danych połączeniach
     *
     * @return lista z informacjami o zyskach na danych połączeniach
     * @throws AppBaseException gdy wystąpi problem z bazą danych
     */
    @RolesAllowed({Role.GenerateReport})
    public List<Connection> generateReport() throws AppBaseException {
        return connectionFacade.findAll();
    }

}

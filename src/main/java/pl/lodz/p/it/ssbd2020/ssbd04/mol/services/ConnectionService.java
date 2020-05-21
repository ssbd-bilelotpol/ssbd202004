package pl.lodz.p.it.ssbd2020.ssbd04.mol.services;

import pl.lodz.p.it.ssbd2020.ssbd04.entities.Connection;
import pl.lodz.p.it.ssbd2020.ssbd04.exceptions.AppBaseException;
import pl.lodz.p.it.ssbd2020.ssbd04.interceptors.TrackingInterceptor;
import pl.lodz.p.it.ssbd2020.ssbd04.mol.dto.ConnectionQueryDto;
import pl.lodz.p.it.ssbd2020.ssbd04.mol.facades.ConnectionFacade;
import pl.lodz.p.it.ssbd2020.ssbd04.security.Role;

import javax.annotation.security.PermitAll;
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
     * Wyszukuje połączenia na podstawie przekazanego kryterium.
     * @param query kryterium
     * @return połączenia spełniające podane kryterium
     */
    @PermitAll
    public List<Connection> find(ConnectionQueryDto query) {
        throw new UnsupportedOperationException();
    }

    /**
     * Zwraca połączenie o podanym identyfikatorze.
     * @param id identyfikator połączenia
     * @return połączenie o podanym identyfikatorze
     * @throws AppBaseException w przypadku niepowodzenia operacji
     */
    @PermitAll
    public Connection findById(Long id) {
        throw new UnsupportedOperationException();
    }

    /**
     * Zapisuje w bazie połączenie.
     * @param connection nowe połączenie
     * @return stworzone połączenie
     * @throws AppBaseException w przypadku niepowodzenia operacji
     */
    @RolesAllowed(Role.CreateConnection)
    public Connection create(Connection connection) throws AppBaseException {
        throw new UnsupportedOperationException();
    }

    /**
     * Usuwa połączenie o podanym identyfikatorze.
     * @param id identyfikator połączenia do usunięcia
     * @throws AppBaseException w przypadku niepowodzenia operacji
     */
    @RolesAllowed(Role.DeleteConnection)
    public void delete(Long id) throws AppBaseException {
        throw new UnsupportedOperationException();
    }

    /**
     * Modyfikuje istniejące połączenie.
     * @param connection zmodyfikowane dane połączenia
     * @throws AppBaseException w przypadku niepowodzenia operacji
     */
    @RolesAllowed(Role.UpdateConnection)
    public void update(Connection connection) throws AppBaseException {
        throw new UnsupportedOperationException();
    }
}

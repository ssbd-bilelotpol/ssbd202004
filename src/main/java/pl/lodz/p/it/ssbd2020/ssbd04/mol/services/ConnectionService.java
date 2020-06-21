package pl.lodz.p.it.ssbd2020.ssbd04.mol.services;

import pl.lodz.p.it.ssbd2020.ssbd04.entities.Connection;
import pl.lodz.p.it.ssbd2020.ssbd04.exceptions.AirportException;
import pl.lodz.p.it.ssbd2020.ssbd04.exceptions.AppBaseException;
import pl.lodz.p.it.ssbd2020.ssbd04.exceptions.ConnectionException;
import pl.lodz.p.it.ssbd2020.ssbd04.interceptors.TrackingInterceptor;
import pl.lodz.p.it.ssbd2020.ssbd04.mol.facades.AirportFacade;
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

import static pl.lodz.p.it.ssbd2020.ssbd04.common.Utils.isNullOrEmpty;

/**
 * Przetwarzanie logiki biznesowej połączeń.
 */
@Interceptors({TrackingInterceptor.class})
@Stateless
@TransactionAttribute(TransactionAttributeType.MANDATORY)
public class ConnectionService {
    @Inject
    private ConnectionFacade connectionFacade;

    @Inject
    private AccountService accountService;

    @Inject
    private AirportFacade airportFacade;

    /**
     * Wyszukuje połączenia pomiędzy lotniskami o danych kodach.
     * @param destinationCode kod lotniska przylotu
     * @param sourceCode kod lotniska wylotu
     * @return połączenia spełniające podane kryterium
     */
    @PermitAll
    public List<Connection> find(String destinationCode, String sourceCode) throws AppBaseException {
        if (!isNullOrEmpty(destinationCode) && !isNullOrEmpty(sourceCode)) {
            return connectionFacade.find(destinationCode, sourceCode);
        } else if (!isNullOrEmpty(destinationCode)) {
            return connectionFacade.findByDestination(destinationCode);
        } else if (!isNullOrEmpty(sourceCode)) {
            return connectionFacade.findBySource(sourceCode);
        } else {
            return connectionFacade.findAll();
        }
    }

    /**
     * Wszykuje połączenie na podstawie frazy.
     * @param phrase fraza do szukania(np. WSZ - LDZ)
     * @return znalezione połączenia
     * @throws AppBaseException w przypadku niepowodzenia operacji
     */
    @PermitAll
    public List<Connection> findByPhrase(String phrase) throws AppBaseException {
        return connectionFacade.findByPhrase(phrase);
    }

    /**
     * Zwraca połączenie o podanym identyfikatorze.
     * @param id identyfikator połączenia
     * @return połączenie o podanym identyfikatorze
     * @throws AppBaseException w przypadku niepowodzenia operacji
     */
    @PermitAll
    public Connection findById(Long id) throws AppBaseException {
        return connectionFacade.find(id);
    }

    /**
     * Zapisuje w bazie połączenie.
     * @param connection nowe połączenie
     * @return stworzone połączenie
     * @throws AppBaseException w przypadku niepowodzenia operacji
     */
    @RolesAllowed(Role.CreateConnection)
    public Connection create(Connection connection, String destinationCode, String sourceCode) throws AppBaseException {
        if (destinationCode.equals(sourceCode)) {
            throw ConnectionException.sameSrcDst();
        }
        try {
            connection.setDestination(airportFacade.find(destinationCode));
        } catch (AirportException e) {
            throw ConnectionException.destinationAirportNotFound();
        }
        try {
            connection.setSource(airportFacade.find(sourceCode));
        } catch (AirportException e) {
            throw ConnectionException.sourceAirportNotFound();
        }
        connection.setCreatedBy(accountService.getCurrentUser());
        connectionFacade.create(connection);
        return connection;
    }

    /**
     * Usuwa połączenie o podanym identyfikatorze.
     * @param connection połączenie do usunięcia
     * @throws AppBaseException w przypadku niepowodzenia operacji
     */
    @RolesAllowed(Role.DeleteConnection)
    public void delete(Connection connection) throws AppBaseException {
        connectionFacade.remove(connection);
    }

    /**
     * Modyfikuje istniejące połączenie.
     * @param connection zmodyfikowane dane połączenia
     * @throws AppBaseException w przypadku niepowodzenia operacji
     */
    @RolesAllowed(Role.UpdateConnection)
    public void update(Connection connection) throws AppBaseException {
        connection.setModifiedBy(accountService.getCurrentUser());
        connectionFacade.edit(connection);
    }
}

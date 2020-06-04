package pl.lodz.p.it.ssbd2020.ssbd04.mol.services;

import pl.lodz.p.it.ssbd2020.ssbd04.entities.Connection;
import pl.lodz.p.it.ssbd2020.ssbd04.exceptions.AirportException;
import pl.lodz.p.it.ssbd2020.ssbd04.exceptions.AppBaseException;
import pl.lodz.p.it.ssbd2020.ssbd04.exceptions.ConnectionException;
import pl.lodz.p.it.ssbd2020.ssbd04.interceptors.TrackingInterceptor;
import pl.lodz.p.it.ssbd2020.ssbd04.mol.dto.ConnectionDto;
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
import java.util.stream.Collectors;

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
    private AirportFacade airportFacade;

    /**
     * Wyszukuje połączenia pomiędzy lotniskami o danych kodach.
     * @param destinationCode kod lotniska przylotu
     * @param sourceCode kod lotniska wylotu
     * @return połączenia spełniające podane kryterium
     */
    @PermitAll
    public List<ConnectionDto> find(String destinationCode, String sourceCode) throws AppBaseException {
        if (destinationCode != "" && sourceCode != "") {
            return connectionFacade.find(airportFacade.find(destinationCode), airportFacade.find(sourceCode)).stream().map(ConnectionDto::new).collect(Collectors.toList());
        } else if (destinationCode != "") {
            return connectionFacade.findByDestination(airportFacade.find(destinationCode)).stream().map(ConnectionDto::new).collect(Collectors.toList());
        } else if (sourceCode != "") {
            return connectionFacade.findBySource(airportFacade.find(sourceCode)).stream().map(ConnectionDto::new).collect(Collectors.toList());
        } else {
            throw ConnectionException.emptyQuery();
        }
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

    @RolesAllowed(Role.CalculateConnectionProfit)
    public void calculateConnectionProfit(Connection connection) throws AppBaseException {
        // flights = flightService.findFlightsByConnection()
        // profit = 0
        // counter = 0
        // for flight in flights:
        //  tickets = ticketFacade.findByFlight(flight)
        //  for ticket in tickets:
        //      counter++
        //      profit += ticket.price
        // connection.profit = profit / counter
        // connectionFacade.edit(connection);
        throw new UnsupportedOperationException();
    }
}

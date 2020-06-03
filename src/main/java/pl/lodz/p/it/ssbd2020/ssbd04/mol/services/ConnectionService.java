package pl.lodz.p.it.ssbd2020.ssbd04.mol.services;

import pl.lodz.p.it.ssbd2020.ssbd04.entities.Connection;
import pl.lodz.p.it.ssbd2020.ssbd04.exceptions.AppBaseException;
import pl.lodz.p.it.ssbd2020.ssbd04.exceptions.ConnectionException;
import pl.lodz.p.it.ssbd2020.ssbd04.interceptors.TrackingInterceptor;
import pl.lodz.p.it.ssbd2020.ssbd04.mol.dto.AirportDto;
import pl.lodz.p.it.ssbd2020.ssbd04.mol.dto.ConnectionQueryDto;
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
import javax.persistence.NoResultException;

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
     * Wyszukuje połączenia na podstawie przekazanego kryterium.
     * @param query kryterium
     * @return połączenia spełniające podane kryterium
     */
    @PermitAll
    public Connection find(ConnectionQueryDto query) throws AppBaseException {
        return connectionFacade.find(airportFacade.find(query.getDestinationCode()), airportFacade.find(query.getSourceCode()));
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

//    /**
//     * Zwraca połączenie o zgodnych lotniskach źródłowym i docelowym.
//     * @param sourceCode kod lotniska wylotu.
//     * @param destinationCode kot lotniska przylotu.
//     * @return połączenie spełniające podane kryterium.
//     * @throws AppBaseException
//     */
//    @PermitAll
//    public Connection findByAirports(String sourceCode, String destinationCode) throws AppBaseException {
//        return connectionFacade.findByAirports(sourceCode, destinationCode);
//    }

    /**
     * Zapisuje w bazie połączenie.
     * @param connection nowe połączenie
     * @return stworzone połączenie
     * @throws AppBaseException w przypadku niepowodzenia operacji
     */
    @RolesAllowed(Role.CreateConnection)
    public Connection create(Connection connection, AirportDto sourceAirport, AirportDto destinationAirport) throws AppBaseException {
        try {
            connection.setSource(airportFacade.find(sourceAirport.getCode()));
        } catch (NoResultException e) {
            throw ConnectionException.sourceAirportNotFound();
        }
        try {
            connection.setDestination(airportFacade.find(destinationAirport.getCode()));
        } catch (NoResultException e) {
            throw ConnectionException.destinationAirportNotFound();
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

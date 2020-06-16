package pl.lodz.p.it.ssbd2020.ssbd04.mol.services;

import pl.lodz.p.it.ssbd2020.ssbd04.entities.AirplaneSchema;
import pl.lodz.p.it.ssbd2020.ssbd04.entities.Connection;
import pl.lodz.p.it.ssbd2020.ssbd04.entities.Flight;
import pl.lodz.p.it.ssbd2020.ssbd04.entities.Seat;
import pl.lodz.p.it.ssbd2020.ssbd04.exceptions.AppBaseException;
import pl.lodz.p.it.ssbd2020.ssbd04.interceptors.TrackingInterceptor;
import pl.lodz.p.it.ssbd2020.ssbd04.mol.facades.AirplaneSchemaFacade;
import pl.lodz.p.it.ssbd2020.ssbd04.mol.facades.ConnectionFacade;
import pl.lodz.p.it.ssbd2020.ssbd04.mol.facades.FlightFacade;
import pl.lodz.p.it.ssbd2020.ssbd04.security.Role;

import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;
import javax.interceptor.Interceptors;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Przetwarzanie logiki biznesowej lotów.
 */

@Interceptors({TrackingInterceptor.class})
@Stateless
@TransactionAttribute(TransactionAttributeType.MANDATORY)
public class FlightService {
    @Inject
    private FlightFacade flightFacade;
    @Inject
    private ConnectionFacade connectionFacade;
    @Inject
    private AirplaneSchemaFacade airplaneSchemaFacade;

    /**
     * Wyszukuje loty na podstawie przekazanego kryterium.
     * @param code kod lotu
     * @param connectionId id połączenia
     * @param airplaneId id lotniska
     * @param from data, po której wylatuje lot
     * @param to dat, przed którą wylatuje lot
     * @return loty spełniające podane kryterium
     */
    @PermitAll
    public List<Flight> find(String code, Long connectionId, Long airplaneId, LocalDateTime from, LocalDateTime to)
            throws AppBaseException {
        Connection connection = null;
        AirplaneSchema airplaneSchema = null;
        if(connectionId != null) {
            connection = connectionFacade.find(connectionId);
            if(connection == null)
                return new ArrayList<>();
        }
        if(airplaneId != null) {
            airplaneSchema = airplaneSchemaFacade.find(airplaneId);
            if(airplaneSchema == null)
                return new ArrayList<>();
        }
        return flightFacade.find(code, connection, airplaneSchema, from, to);
    }

    /**
     * Zwraca loty o podanym identyfikatorze.
     * @param code identyfikator lotu
     * @return lot o podanym identyfikatorze
     * @throws AppBaseException w przypadku niepowodzenia operacji
     */
    @PermitAll
    public Flight findByCode(String code) throws AppBaseException {
        return flightFacade.find(code);
    }

    /**
     * Zwraca loty przypisane do danego schematu samolotu
     * @param airplaneSchema schemat samolotu
     * @return lista przypisanych lotów
     * @throws AppBaseException
     */
    @RolesAllowed(Role.UpdateAirplaneSchema)
    public List<Flight> findByAirplaneSchema(AirplaneSchema airplaneSchema) throws AppBaseException {
        return flightFacade.findByAirplaneSchema(airplaneSchema);
    }

    /**
     * Tworzy i zapisuje w bazie lot.
     * @param flight nowy lot
     * @param connection połączenie, którego lot dotyczy
     * @param airplaneSchema schemat siedzeń przypisany do samolotu
     * @return stworzony lot
     * @throws AppBaseException w przypadku niepowodzenia operacji
     */
    @RolesAllowed(Role.CreateFlight)
    public Flight create(Flight flight, Connection connection, AirplaneSchema airplaneSchema) throws AppBaseException {
        flight.setConnection(connection);
        flight.setAirplaneSchema(airplaneSchema);
        flightFacade.create(flight);
        return flight;
    }

    /**
     * Anuluje lot o podanym identyfikatorze.
     * @param flight lotu do anulowania
     * @throws AppBaseException w przypadku niepowodzenia operacji
     */
    @RolesAllowed(Role.CancelFlight)
    public void cancel(Flight flight) throws AppBaseException {
        throw new UnsupportedOperationException();
    }

    /**
     * Modyfikuje istniejący lot.
     * @param flight zmodyfikowane dane lotu
     * @throws AppBaseException w przypadku niepowodzenia operacji
     */
    @RolesAllowed(Role.UpdateFlight)
    public void update(Flight flight, AirplaneSchema airplaneSchema) throws AppBaseException {
        throw new UnsupportedOperationException();
    }

    @RolesAllowed(Role.GetTakenSeats)
    public List<Seat> getTakenSeats(Flight flight) throws AppBaseException {
        // tickets = ticketFacade.findByFlight(flight)
        // seats = emptyList();
        // for ticket in tickets:
        //  for passenger in passengers:
        //      seats.add(passenger.getSeat());
        // return seats
        throw new UnsupportedOperationException();
    }

    @RolesAllowed(Role.CalculateConnectionProfit)
    public List<Flight> findByConnection(Connection connection) throws AppBaseException {
        throw new UnsupportedOperationException();
    }
}

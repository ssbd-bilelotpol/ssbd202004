package pl.lodz.p.it.ssbd2020.ssbd04.mol.services;

import pl.lodz.p.it.ssbd2020.ssbd04.entities.AirplaneSchema;
import pl.lodz.p.it.ssbd2020.ssbd04.entities.Connection;
import pl.lodz.p.it.ssbd2020.ssbd04.entities.Flight;
import pl.lodz.p.it.ssbd2020.ssbd04.entities.Seat;
import pl.lodz.p.it.ssbd2020.ssbd04.exceptions.AppBaseException;
import pl.lodz.p.it.ssbd2020.ssbd04.interceptors.TrackingInterceptor;
import pl.lodz.p.it.ssbd2020.ssbd04.mol.dto.FlightQueryDto;
import pl.lodz.p.it.ssbd2020.ssbd04.mol.facades.FlightFacade;
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
 * Przetwarzanie logiki biznesowej lotów.
 */

@Interceptors({TrackingInterceptor.class})
@Stateless
@TransactionAttribute(TransactionAttributeType.MANDATORY)
public class FlightService {
    @Inject
    private FlightFacade flightFacade;

    /**
     * Wyszukuje loty na podstawie przekazanego kryterium.
     * @param query kryterium
     * @return loty spełniające podane kryterium
     */
    @PermitAll
    public List<Flight> find(FlightQueryDto query) {
        throw new UnsupportedOperationException();
    }

    /**
     * Zwraca loty o podanym identyfikatorze.
     * @param id identyfikator lotu
     * @return lot o podanym identyfikatorze
     * @throws AppBaseException w przypadku niepowodzenia operacji
     */
    @PermitAll
    public Flight findById(Long id) {
        throw new UnsupportedOperationException();
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
        throw new UnsupportedOperationException();
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

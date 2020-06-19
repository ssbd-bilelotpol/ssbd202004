package pl.lodz.p.it.ssbd2020.ssbd04.mob.endpoints;

import pl.lodz.p.it.ssbd2020.ssbd04.common.AbstractEndpoint;
import pl.lodz.p.it.ssbd2020.ssbd04.entities.*;
import pl.lodz.p.it.ssbd2020.ssbd04.exceptions.AppBaseException;
import pl.lodz.p.it.ssbd2020.ssbd04.exceptions.FlightException;
import pl.lodz.p.it.ssbd2020.ssbd04.exceptions.SeatClassException;
import pl.lodz.p.it.ssbd2020.ssbd04.exceptions.SeatException;
import pl.lodz.p.it.ssbd2020.ssbd04.interceptors.TrackingInterceptor;
import pl.lodz.p.it.ssbd2020.ssbd04.mob.dto.*;
import pl.lodz.p.it.ssbd2020.ssbd04.mob.services.AccountService;
import pl.lodz.p.it.ssbd2020.ssbd04.mob.services.FlightService;
import pl.lodz.p.it.ssbd2020.ssbd04.mob.services.SeatService;
import pl.lodz.p.it.ssbd2020.ssbd04.mob.services.TicketService;
import pl.lodz.p.it.ssbd2020.ssbd04.mol.dto.SeatDto;
import pl.lodz.p.it.ssbd2020.ssbd04.security.Role;

import javax.annotation.security.RolesAllowed;
import javax.ejb.Stateful;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;
import javax.interceptor.Interceptors;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Wykonuje konwersję klas DTO na model biznesowy
 * i jest granicą transakcji aplikacyjnej dla hierarchii klas Ticket.
 */
@Interceptors({TrackingInterceptor.class})
@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
@Stateful
public class TicketEndpointImpl extends AbstractEndpoint implements TicketEndpoint {

    @Inject
    private AccountService accountService;

    @Inject
    private TicketService ticketService;

    @Inject
    private FlightService flightService;

    @Inject
    private SeatService seatService;

    @Override
    @RolesAllowed(Role.FindTicketById)
    public TicketDto findById(Long id) throws AppBaseException {
        throw new UnsupportedOperationException();
    }

    @Override
    @RolesAllowed(Role.FindTicketsByFlight)
    public List<TicketDto> findByFlight(Long id) throws AppBaseException {
        throw new UnsupportedOperationException();
    }

    @Override
    @RolesAllowed(Role.FindTicketsByAccount)
    public List<TicketDto> findByAccount(Long id) throws AppBaseException {
        return null;
    }

    @Override
    @RolesAllowed(Role.GetAllTickets)
    public List<TicketDto> getAllTickets() throws AppBaseException {
        throw new UnsupportedOperationException();
    }

    @Override
    @RolesAllowed(Role.GetOwnTickets)
    public List<TicketDto> getOwnTickets() throws AppBaseException {
        throw new UnsupportedOperationException();
    }

    @Override
    @RolesAllowed(Role.CreateTicket)
    public void buyTicket(TicketBuyDto ticketDto) throws AppBaseException {
        if (ticketDto.getDestinationFlight() != null) {
            Flight destinationFlight = flightService.findByCode(ticketDto.getDestinationFlight().getCode());
            buySingleTicket(destinationFlight, ticketDto.getDestinationFlight(), ticketDto.getPassengers());
        }

        if (ticketDto.getReturnFlight() != null) {
            Flight returnFlight = flightService.findByCode(ticketDto.getReturnFlight().getCode());
            buySingleTicket(returnFlight, ticketDto.getReturnFlight(), ticketDto.getPassengers());
        }
    }

    private void buySingleTicket(Flight flight, SelectedFlightDto flightInfo, List<CreatePassengerDto> passengers) throws AppBaseException {
        Account currentUser = accountService.getCurrentUser();
        LinkedList<Seat> seats = new LinkedList<>();

        if (!flight.getStartDateTime().isEqual(flightInfo.getExpectedDepartureTime())) {
            throw FlightException.departureTimeChanged();
        }

        if (flight.getStartDateTime().isBefore(LocalDateTime.now())) {
            throw FlightException.alreadyDeparted();
        }

        if (flight.getStatus() == FlightStatus.CANCELLED || flight.getStatus() == FlightStatus.INACTIVE) {
            throw FlightException.cancelled();
        }

        if (flight.getPrice().compareTo(flightInfo.getExpectedPrice()) != 0) {
            throw FlightException.priceChanged();
        }

        for (SelectedSeatDto seatDto : flightInfo.getSeats()) {
            Seat seat = seatService.findById(seatDto.getId());
            if (!seat.getAirplaneSchema().getId().equals(flight.getAirplaneSchema().getId())) {
                throw SeatException.notFound();
            }

            if (seat.getSeatClass().getPrice().compareTo(seatDto.getExpectedPrice()) != 0) {
                throw SeatClassException.priceChanged(seat.getSeatClass());
            }

            seats.add(seat);
        }

        List<Passenger> destinationPassengers = passengers
                .stream()
                .map(dto -> new Passenger(dto.getFirstName(), dto.getLastName(), dto.getEmail(), dto.getPhoneNumber(), seats.pop()))
                .collect(Collectors.toList());

        // Calculate ticket price
        BigDecimal totalPrice = flight.getPrice().multiply(BigDecimal.valueOf(destinationPassengers.size()));
        for (Seat seat : seats) {
            totalPrice = totalPrice.add(seat.getSeatClass().getPrice());
        }

        Ticket destinationTicket = new Ticket(flight, totalPrice, currentUser, new HashSet<>());
        destinationTicket.setCreatedBy(currentUser);

        for (Passenger passenger : destinationPassengers) {
            passenger.setTicket(destinationTicket);
            passenger.setCreatedBy(currentUser);
            passenger.setFlight(flight);
        }

        destinationTicket.setPassengers(new HashSet<>(destinationPassengers));
        ticketService.buyTicket(destinationTicket);
    }

    @Override
    @RolesAllowed(Role.ReturnTicket)
    public void returnTicket(TicketReturnDto ticketDto) throws AppBaseException {
        throw new UnsupportedOperationException();
    }

    @Override
    @RolesAllowed(Role.UpdateTicket)
    public void update(Long id, TicketUpdateDto ticketUpdateDto) throws AppBaseException {
        throw new UnsupportedOperationException();
    }

    @Override
    @RolesAllowed(Role.GenerateReport)
    public ReportDto generateReport() throws AppBaseException {
        return null;
    }

}

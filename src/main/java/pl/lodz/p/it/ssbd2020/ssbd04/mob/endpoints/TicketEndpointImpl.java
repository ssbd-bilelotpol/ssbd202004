package pl.lodz.p.it.ssbd2020.ssbd04.mob.endpoints;

import pl.lodz.p.it.ssbd2020.ssbd04.common.AbstractEndpoint;
import pl.lodz.p.it.ssbd2020.ssbd04.entities.*;
import pl.lodz.p.it.ssbd2020.ssbd04.exceptions.*;
import pl.lodz.p.it.ssbd2020.ssbd04.interceptors.TrackingInterceptor;
import pl.lodz.p.it.ssbd2020.ssbd04.mob.dto.*;
import pl.lodz.p.it.ssbd2020.ssbd04.mob.services.*;
import pl.lodz.p.it.ssbd2020.ssbd04.security.Role;

import javax.annotation.security.RolesAllowed;
import javax.ejb.Stateful;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;
import javax.interceptor.Interceptors;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
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
    private ConnectionService connectionService;

    @Inject
    private FlightService flightService;

    @Inject
    private SeatService seatService;

    @Override
    @RolesAllowed({Role.FindTicketById, Role.FindAnyTicketById})
    public TicketDto findById(Long id) throws AppBaseException {
        Ticket ticket = ticketService.findById(id);
        Account account = accountService.getCurrentUser();

        boolean canAccessAny = accountService.hasRole(Role.FindAnyTicketById);
        if (!canAccessAny && !ticket.getAccount().getId().equals(account.getId())) {
            throw ForbiddenException.forbidden();
        }

        return new TicketDto(ticket);
    }

    @Override
    @RolesAllowed(Role.GetOwnTickets)
    public List<TicketDto> getOwnTickets() throws AppBaseException {
        Account account = accountService.getCurrentUser();
        return ticketService.findByAccount(account)
                .stream()
                .map(TicketDto::new)
                .collect(Collectors.toList());
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

        if (flight.getStartDateTime().isBefore(LocalDateTime.now(ZoneOffset.UTC))) {
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

        // Calculate ticket price
        BigDecimal totalPrice = flight.getPrice().multiply(BigDecimal.valueOf(passengers.size()));
        for (Seat seat : seats) {
            totalPrice = totalPrice.add(seat.getSeatClass().getPrice());
        }

        List<Passenger> destinationPassengers = passengers
                .stream()
                .map(dto -> new Passenger(dto.getFirstName(), dto.getLastName(), dto.getEmail(), dto.getPhoneNumber(), seats.pop()))
                .collect(Collectors.toList());

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
    @RolesAllowed({Role.ReturnTicket, Role.ReturnAnyTicket})
    public void returnTicket(Long id) throws AppBaseException {
        Account account = accountService.getCurrentUser();
        Ticket ticket = ticketService.findById(id);

        boolean canAccessAny = accountService.hasRole(Role.ReturnAnyTicket);
        if (!canAccessAny && !ticket.getAccount().getId().equals(account.getId())) {
            throw ForbiddenException.forbidden();
        }

        LocalDateTime expirationTime = ticket.getFlight().getStartDateTime().minusHours(72);
        if (LocalDateTime.now(ZoneOffset.UTC).isAfter(expirationTime)) {
            throw TicketException.timeToReturnExpired();
        }

        ticketService.returnTicket(ticket);
    }

    @Override
    @RolesAllowed(Role.UpdateTicket)
    public void update(Long id, TicketUpdateDto ticketUpdateDto) throws AppBaseException {
        Ticket ticket = ticketService.findById(ticketUpdateDto.getTicketId());

        TicketDto ticketDto = new TicketDto(ticket);
        if (!verifyEtag(ticketDto)) throw AppBaseException.optimisticLock();

        for (UpdatePassengerDto passengerDto : ticketUpdateDto.getPassengers()) {
            List<Passenger> searchResults = ticket.getPassengers()
                    .stream()
                    .filter(passenger -> passenger.getId().equals(passengerDto.getId()))
                    .collect(Collectors.toList());

            if (searchResults.isEmpty()) {
                throw PassengerException.notFound();
            }

            Passenger passenger = searchResults.get(0);
            passenger.setFirstName(passengerDto.getFirstName());
            passenger.setLastName(passengerDto.getLastName());
            passenger.setEmail(passengerDto.getEmail());
            passenger.setPhoneNumber(passengerDto.getPhoneNumber());
        }

        ticketService.update(ticket);
    }

    @Override
    @RolesAllowed(Role.GenerateReport)
    public List<ReportDto> generateReport() throws AppBaseException {
        List<Connection> connections = connectionService.generateReport();
        return connections.stream().map(connection -> new ReportDto(
                connection.getId(),
                connection.getSource().getCode(),
                connection.getDestination().getCode(),
                connection.getProfit()
        )).collect(Collectors.toList());
    }

    @Override
    @RolesAllowed(Role.FindTicketsByFlights)
    public List<TicketListDto> find(String code, Long connectionId, Long airplaneId,
                                    LocalDateTime from, LocalDateTime to) throws AppBaseException {
        List<Long> flightIds = flightService.find(code, connectionId, airplaneId)
                .stream()
                .map(Flight::getId)
                .collect(Collectors.toList());
        return ticketService.findByFlights(flightIds)
                .stream()
                .filter(t -> from == null || t.getCreationDateTime().isAfter(from))
                .filter(t -> to == null || t.getCreationDateTime().isBefore(to))
                .map(TicketListDto::new)
                .collect(Collectors.toList());
    }

}

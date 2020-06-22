package pl.lodz.p.it.ssbd2020.ssbd04.mol.endpoints;

import pl.lodz.p.it.ssbd2020.ssbd04.common.AbstractEndpoint;
import pl.lodz.p.it.ssbd2020.ssbd04.entities.AirplaneSchema;
import pl.lodz.p.it.ssbd2020.ssbd04.entities.Connection;
import pl.lodz.p.it.ssbd2020.ssbd04.entities.Flight;
import pl.lodz.p.it.ssbd2020.ssbd04.entities.FlightStatus;
import pl.lodz.p.it.ssbd2020.ssbd04.exceptions.AppBaseException;
import pl.lodz.p.it.ssbd2020.ssbd04.interceptors.TrackingInterceptor;
import pl.lodz.p.it.ssbd2020.ssbd04.mol.dto.FlightCreateDto;
import pl.lodz.p.it.ssbd2020.ssbd04.mol.dto.FlightDto;
import pl.lodz.p.it.ssbd2020.ssbd04.mol.dto.FlightEditDto;
import pl.lodz.p.it.ssbd2020.ssbd04.mol.dto.SeatDto;
import pl.lodz.p.it.ssbd2020.ssbd04.mol.services.*;
import pl.lodz.p.it.ssbd2020.ssbd04.security.Role;

import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;
import javax.ejb.Stateful;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;
import javax.interceptor.Interceptors;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Interceptors({TrackingInterceptor.class})
@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
@Stateful
public class FlightEndpointImpl extends AbstractEndpoint implements FlightEndpoint {

    @Inject
    private FlightService flightService;

    @Inject
    private ConnectionService connectionService;

    @Inject
    private AirplaneSchemaService schemaService;

    @Inject
    private AccountService accountService;

    @Inject
    private SeatService seatService;

    @Override
    @PermitAll
    public List<FlightDto> find(String code, Long connection, Long airplane,
                                LocalDateTime from, LocalDateTime to, FlightStatus flightStatus) throws AppBaseException {
        return flightService.find(code,
                connection,
                airplane,
                from,
                to,
                flightStatus)
                .stream()
                .map(FlightDto::new)
                .collect(Collectors.toList());
    }

    @Override
    @PermitAll
    public List<LocalDate> getDates(LocalDateTime from) throws AppBaseException {
        return flightService.getDates(from);
    }

    @Override
    @PermitAll
    public FlightDto findByCode(String code) throws AppBaseException {
        return new FlightDto(flightService.findByCode(code));
    }

    @Override
    @RolesAllowed(Role.CreateFlight)
    public FlightDto create(FlightCreateDto flightDto) throws AppBaseException {
        Flight flight = new Flight(flightDto.getFlightCode(),
                flightDto.getPrice(),
                null,
                null,
                flightDto.getDepartureTime(),
                flightDto.getArrivalTime(),
                FlightStatus.INACTIVE);
        Connection connection = connectionService.findById(flightDto.getConnectionId());
        AirplaneSchema airplaneSchema = schemaService.findById(flightDto.getAirplaneSchemaId());
        flight.setCreatedBy(accountService.getCurrentUser());
        return new FlightDto(flightService.create(flight, connection, airplaneSchema));
    }

    @Override
    @RolesAllowed(Role.CancelFlight)
    public void cancel(String code) throws AppBaseException {
        Flight flight = flightService.findByCode(code, true);
        FlightDto currentFlightDto = new FlightDto(flight);
        if (!verifyEtag(currentFlightDto)) {
            throw AppBaseException.optimisticLock();
        }
        flightService.cancel(flight);
    }

    @Override
    @RolesAllowed(Role.UpdateFlight)
    public void update(String code, FlightEditDto flightDto) throws AppBaseException {
        Flight flight = flightService.findByCode(code, true);
        FlightDto currentFlightDto = new FlightDto(flight);
        if (!verifyEtag(currentFlightDto)) {
            throw AppBaseException.optimisticLock();
        }
        if (!flight.getStatus().equals(FlightStatus.CANCELLED)) {
            flight.setStatus(flightDto.getActive() ? FlightStatus.ACTIVE : FlightStatus.INACTIVE);
        }
        flight.setPrice(flightDto.getPrice());
        flightService.update(flight, flightDto.getDepartureTime(), flightDto.getArrivalTime());
    }

    @Override
    @RolesAllowed(Role.GetTakenSeats)
    public List<SeatDto> getTakenSeats(String code) throws AppBaseException {
        return seatService.getTakenSeats(flightService.findByCode(code))
                .stream()
                .map(s -> new SeatDto(s.getId(), s.getSeatClass().getName(), s.getCol(), s.getRow())).collect(Collectors.toList());
    }

}

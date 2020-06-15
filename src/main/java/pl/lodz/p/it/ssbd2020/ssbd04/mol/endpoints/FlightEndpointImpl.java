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
import pl.lodz.p.it.ssbd2020.ssbd04.mol.dto.SeatDto;
import pl.lodz.p.it.ssbd2020.ssbd04.mol.services.AirplaneSchemaService;
import pl.lodz.p.it.ssbd2020.ssbd04.mol.services.ConnectionService;
import pl.lodz.p.it.ssbd2020.ssbd04.mol.services.FlightService;
import pl.lodz.p.it.ssbd2020.ssbd04.security.Role;
import pl.lodz.p.it.ssbd2020.ssbd04.services.EmailService;

import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;
import javax.ejb.Stateful;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;
import javax.interceptor.Interceptors;
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
    private EmailService emailService;

    @Override
    @PermitAll
    public List<FlightDto> find(String code, Long connection, Long airplane,
                                LocalDateTime from, LocalDateTime to) throws AppBaseException {
        return flightService.find(code,
                connection,
                airplane,
                from,
                to)
                .stream()
                .map(FlightDto::new)
                .collect(Collectors.toList());
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
                FlightStatus.ACTIVE);
        Connection connection = connectionService.findById(flightDto.getConnectionId());
        AirplaneSchema airplaneSchema = schemaService.findById(flightDto.getAirplaneSchemaId());
        return new FlightDto(flightService.create(flight, connection, airplaneSchema));
    }

    @Override
    @RolesAllowed(Role.CancelFlight)
    public void cancel(Long id) throws AppBaseException {
        throw new UnsupportedOperationException();
    }

    @Override
    @RolesAllowed(Role.UpdateFlight)
    public void update(Long id, FlightDto flightDto) throws AppBaseException {
        throw new UnsupportedOperationException();
    }

    @Override
    @RolesAllowed(Role.GetTakenSeats)
    public List<SeatDto> getTakenSeats(Long id) throws AppBaseException {
        // throws: FlightNotFound
        throw new UnsupportedOperationException();
    }
}

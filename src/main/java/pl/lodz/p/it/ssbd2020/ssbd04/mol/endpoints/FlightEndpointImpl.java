package pl.lodz.p.it.ssbd2020.ssbd04.mol.endpoints;

import pl.lodz.p.it.ssbd2020.ssbd04.exceptions.AppBaseException;
import pl.lodz.p.it.ssbd2020.ssbd04.interceptors.TrackingInterceptor;
import pl.lodz.p.it.ssbd2020.ssbd04.mol.dto.FlightDto;
import pl.lodz.p.it.ssbd2020.ssbd04.mol.dto.FlightQueryDto;
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
import java.util.List;

@Interceptors({TrackingInterceptor.class})
@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
@Stateful
public class FlightEndpointImpl implements FlightEndpoint {
    @Inject
    private FlightService fightService;
    @Inject
    private ConnectionService connectionService;
    @Inject
    private AirplaneSchemaService schemaService;
    @Inject
    private EmailService emailService;

    @Override
    @PermitAll
    public List<FlightDto> find(FlightQueryDto query) {
        throw new UnsupportedOperationException();
    }

    @Override
    @PermitAll
    public FlightDto findById(Long id) throws AppBaseException {
        throw new UnsupportedOperationException();
    }

    @Override
    @RolesAllowed(Role.CreateFlight)
    public FlightDto create(FlightDto flightDto) throws AppBaseException {
        throw new UnsupportedOperationException();
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

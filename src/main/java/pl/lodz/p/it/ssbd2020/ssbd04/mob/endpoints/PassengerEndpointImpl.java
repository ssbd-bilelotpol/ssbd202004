package pl.lodz.p.it.ssbd2020.ssbd04.mob.endpoints;

import pl.lodz.p.it.ssbd2020.ssbd04.common.AbstractEndpoint;
import pl.lodz.p.it.ssbd2020.ssbd04.exceptions.AppBaseException;
import pl.lodz.p.it.ssbd2020.ssbd04.interceptors.TrackingInterceptor;
import pl.lodz.p.it.ssbd2020.ssbd04.mob.dto.PassengerDto;
import pl.lodz.p.it.ssbd2020.ssbd04.mob.services.PassengerService;
import pl.lodz.p.it.ssbd2020.ssbd04.security.Role;

import javax.annotation.security.RolesAllowed;
import javax.ejb.Stateful;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;
import javax.interceptor.Interceptors;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Wykonuje konwersję klas DTO na model biznesowy
 * i jest granicą transakcji aplikacyjnej dla hierarchii klas Passenger.
 */
@Interceptors({TrackingInterceptor.class})
@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
@Stateful
public class PassengerEndpointImpl extends AbstractEndpoint implements PassengerEndpoint {

    @Inject
    private PassengerService passengerService;

    @Override
    @RolesAllowed(Role.FindClientsByName)
    public List<PassengerDto> find(String name, String flightCode) throws AppBaseException {
        return passengerService.find(name, flightCode).stream().map(PassengerDto::new).collect(Collectors.toList());
    }

}

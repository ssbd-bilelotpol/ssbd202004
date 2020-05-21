package pl.lodz.p.it.ssbd2020.ssbd04.mol.endpoints;

import pl.lodz.p.it.ssbd2020.ssbd04.common.AbstractEndpoint;
import pl.lodz.p.it.ssbd2020.ssbd04.exceptions.AppBaseException;
import pl.lodz.p.it.ssbd2020.ssbd04.interceptors.TrackingInterceptor;
import pl.lodz.p.it.ssbd2020.ssbd04.mol.dto.AirportDto;
import pl.lodz.p.it.ssbd2020.ssbd04.mol.dto.AirportQueryDto;
import pl.lodz.p.it.ssbd2020.ssbd04.mol.services.AirportService;
import pl.lodz.p.it.ssbd2020.ssbd04.security.Role;

import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;
import javax.ejb.Stateful;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;
import javax.interceptor.Interceptors;
import java.util.List;

/**
 * Wykonuje konwersję klas DTO na model biznesowy
 * i jest granicą transakcji aplikacyjnej dla hierarchii klas Airport.
 */
@Interceptors({TrackingInterceptor.class})
@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
@Stateful
public class AirportEndpointImpl extends AbstractEndpoint implements AirportEndpoint {
    @Inject
    private AirportService airportService;

    @Override
    @PermitAll
    public List<AirportDto> find(AirportQueryDto query) {
        throw new UnsupportedOperationException();
    }

    @Override
    @PermitAll
    public AirportDto findById(Long id) throws AppBaseException {
        throw new UnsupportedOperationException();
    }

    @Override
    @RolesAllowed(Role.CreateAirport)
    public AirportDto create(AirportDto airportDto) throws AppBaseException {
        throw new UnsupportedOperationException();
    }

    @Override
    @RolesAllowed(Role.DeleteAirport)
    public void delete(Long id) throws AppBaseException {
        throw new UnsupportedOperationException();
    }

    @Override
    @RolesAllowed(Role.UpdateAirport)
    public void update(Long id, AirportDto airportDto) throws AppBaseException {
        throw new UnsupportedOperationException();
    }
}

package pl.lodz.p.it.ssbd2020.ssbd04.mol.endpoints;

import pl.lodz.p.it.ssbd2020.ssbd04.common.AbstractEndpoint;
import pl.lodz.p.it.ssbd2020.ssbd04.entities.Airport;
import pl.lodz.p.it.ssbd2020.ssbd04.exceptions.AppBaseException;
import pl.lodz.p.it.ssbd2020.ssbd04.interceptors.TrackingInterceptor;
import pl.lodz.p.it.ssbd2020.ssbd04.mol.dto.AirportDto;
import pl.lodz.p.it.ssbd2020.ssbd04.mol.services.AccountService;
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

    @Inject
    private AccountService accountService;

    @Override
    @PermitAll
    public List<AirportDto> find(String name, String code, String country, String city) throws AppBaseException {
        return airportService.find(name, code, country, city);
    }

    @Override
    @PermitAll
    public AirportDto findByCode(String code) throws AppBaseException {
        return new AirportDto(airportService.findByCode(code));
    }

    @PermitAll
    public List<String> getCountries() throws AppBaseException {
        return airportService.getCountries();
    }

    @PermitAll
    public List<String> getCities() throws AppBaseException {
        return airportService.getCities();
    }

    @Override
    @RolesAllowed(Role.CreateAirport)
    public AirportDto create(AirportDto airportDto) throws AppBaseException {
        Airport airport = new Airport(airportDto.getCode(), airportDto.getName(), airportDto.getCountry(),
                airportDto.getCity());
        airport.setCreatedBy(accountService.getCurrentUser());

        return new AirportDto(airportService.create(airport));
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

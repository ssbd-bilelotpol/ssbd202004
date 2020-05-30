package pl.lodz.p.it.ssbd2020.ssbd04.mol.endpoints;

import pl.lodz.p.it.ssbd2020.ssbd04.common.AbstractEndpoint;
import pl.lodz.p.it.ssbd2020.ssbd04.entities.Airport;
import pl.lodz.p.it.ssbd2020.ssbd04.exceptions.AppBaseException;
import pl.lodz.p.it.ssbd2020.ssbd04.interceptors.TrackingInterceptor;
import pl.lodz.p.it.ssbd2020.ssbd04.mol.dto.AirportCreateDto;
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
import java.util.Set;
import java.util.stream.Collectors;

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
    public AirportDto findById(Long id) throws AppBaseException {
        return new AirportDto(airportService.findById(id));
    }

    @PermitAll
    public Set<String> getCountries() throws AppBaseException {
        List<Airport> airports = airportService.findAll();
        return airports.stream().map(Airport::getCountry).collect(Collectors.toSet());
    }

    @PermitAll
    public Set<String> getCities() throws AppBaseException {
        List<Airport> airports = airportService.findAll();
        return airports.stream().map(Airport::getCity).collect(Collectors.toSet());
    }

    @Override
    @RolesAllowed(Role.CreateAirport)
    public AirportDto create(AirportCreateDto airportCreateDto) throws AppBaseException {
        Airport airport = new Airport();
        airport.setCode(airportCreateDto.getCode());
        airport.setName(airportCreateDto.getName());
        airport.setCity(airportCreateDto.getCity());
        airport.setCountry(airportCreateDto.getCountry());
//        airport.setCreatedBy(this.accountService.getCurrentUser());

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

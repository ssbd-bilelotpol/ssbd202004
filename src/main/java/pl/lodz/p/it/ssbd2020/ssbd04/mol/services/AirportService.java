package pl.lodz.p.it.ssbd2020.ssbd04.mol.services;

import pl.lodz.p.it.ssbd2020.ssbd04.entities.Airport;
import pl.lodz.p.it.ssbd2020.ssbd04.exceptions.AppBaseException;
import pl.lodz.p.it.ssbd2020.ssbd04.interceptors.TrackingInterceptor;
import pl.lodz.p.it.ssbd2020.ssbd04.mol.dto.AirportDto;
import pl.lodz.p.it.ssbd2020.ssbd04.mol.facades.AirportFacade;
import pl.lodz.p.it.ssbd2020.ssbd04.security.Role;

import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;
import javax.interceptor.Interceptors;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Przetwarzanie logiki biznesowej lotnisk.
 */
@Interceptors({TrackingInterceptor.class})
@Stateless
@TransactionAttribute(TransactionAttributeType.MANDATORY)
public class AirportService {
    @Inject
    private AirportFacade airportFacade;

    /**
     * Wyszukuje lotniska na podstawie przekazanego kryterium.
     * @param city miasto.
     * @param code kod lotniska.
     * @param country kraj.
     * @param name nazwa lotniska.
     * @return lotniska spełniające podane kryterium
     */
    @PermitAll
    public List<AirportDto> find(String name, String code, String country, String city) throws AppBaseException {
        return airportFacade.find(name, code, country, city).stream().map(AirportDto::new).collect(Collectors.toList());
    }

    /**
     * Zwraca lotnisko o podanym identyfikatorze.
     * @param code identyfikator lotniska
     * @return lotnisko o podanym identyfikatorze
     * @throws AppBaseException w przypadku niepowodzenia operacji
     */
    @PermitAll
    public Airport findByCode(String code) throws AppBaseException {
        return airportFacade.find(code);
    }

    /**
     * Zwraca listę wszystkich lotnisk.
     * @return lista wszystkich lotnisk
     * @throws AppBaseException
     */
    @PermitAll
    public List<Airport> findAll() throws AppBaseException {
        return airportFacade.findAll();
    }

    @PermitAll
    public List<String> getCountries() throws AppBaseException {
        return airportFacade.getCountries();
    }

    @PermitAll
    public List<String> getCities() throws AppBaseException {
        return airportFacade.getCities();
    }

    /**
     * Zapisuje w bazie lotnisko.
     * @param airport lotnisko do zapisania.
     * @return stworzone lotnisko
     * @throws AppBaseException w przypadku niepowodzenia operacji
     */
    @RolesAllowed(Role.CreateAirport)
    public Airport create(Airport airport) throws AppBaseException {
        airportFacade.create(airport);
        return airport;
    }

    /**
     * Usuwa lotnisko o podanym identyfikatorze.
     * @param airport lotnisko do usunięcia
     * @throws AppBaseException w przypadku niepowodzenia operacji
     */
    @RolesAllowed(Role.DeleteAirport)
    public void delete(Airport airport) throws AppBaseException {
        throw new UnsupportedOperationException();
    }

    /**
     * Modyfikuje istniejące lotnisko.
     * @param airport zmodyfikowane dane lotniska
     * @throws AppBaseException w przypadku niepowodzenia operacji
     */
    @RolesAllowed(Role.UpdateAirport)
    public void update(Airport airport) throws AppBaseException {
        airportFacade.edit(airport);
    }
}

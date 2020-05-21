package pl.lodz.p.it.ssbd2020.ssbd04.mol.endpoints;

import pl.lodz.p.it.ssbd2020.ssbd04.exceptions.AppBaseException;
import pl.lodz.p.it.ssbd2020.ssbd04.mol.dto.AirportDto;
import pl.lodz.p.it.ssbd2020.ssbd04.mol.dto.AirportQueryDto;
import pl.lodz.p.it.ssbd2020.ssbd04.security.Role;

import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;
import javax.ejb.Local;
import java.util.List;

/**
 * Wykonuje konwersję klas DTO na model biznesowy
 * i jest granicą transakcji aplikacyjnej dla hierarchii klas Airport.
 */
@Local
public interface AirportEndpoint {
    /**
     * Wyszukuje lotniska na podstawie przekazanego kryterium.
     * @param query kryterium
     * @return lotniska spełniające podane kryterium
     */
    @PermitAll
    List<AirportDto> find(AirportQueryDto query);

    /**
     * Zwraca lotnisko o podanym identyfikatorze.
     * @param id identyfikator lotniska
     * @return lotnisko o podanym identyfikatorze
     * @throws AppBaseException w przypadku niepowodzenia operacji
     */
    @PermitAll
    AirportDto findById(Long id) throws AppBaseException;

    /**
     * Tworzy i zapisuje w bazie lotnisko.
     * @param airportDto dane nowego lotniska
     * @return stworzone lotnisko
     * @throws AppBaseException w przypadku niepowodzenia operacji
     */
    @RolesAllowed(Role.CreateAirport)
    AirportDto create(AirportDto airportDto) throws AppBaseException;

    /**
     * Usuwa lotnisko o podanym identyfikatorze.
     * @param id identyfikator lotniska do usunięcia
     * @throws AppBaseException w przypadku niepowodzenia operacji
     */
    @RolesAllowed(Role.DeleteAirport)
    void delete(Long id) throws AppBaseException;

    /**
     * Modyfikuje istniejące lotnisko.
     * @param id identyfikator lotniska, które ma zostać zmodyfikowane
     * @param airportDto dane, które mają zostać zapisane
     * @throws AppBaseException w przypadku niepowodzenia operacji
     */
    @RolesAllowed(Role.UpdateAirport)
    void update(Long id, AirportDto airportDto) throws AppBaseException;
}

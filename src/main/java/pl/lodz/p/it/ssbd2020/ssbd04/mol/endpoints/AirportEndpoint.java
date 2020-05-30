package pl.lodz.p.it.ssbd2020.ssbd04.mol.endpoints;

import pl.lodz.p.it.ssbd2020.ssbd04.common.TransactionStarter;
import pl.lodz.p.it.ssbd2020.ssbd04.exceptions.AppBaseException;
import pl.lodz.p.it.ssbd2020.ssbd04.mol.dto.AirportDto;

import javax.ejb.Local;
import java.util.List;
import java.util.Set;

/**
 * Wykonuje konwersję klas DTO na model biznesowy
 * i jest granicą transakcji aplikacyjnej dla hierarchii klas Airport.
 */
@Local
public interface AirportEndpoint extends TransactionStarter {
    /**
     * Wyszukuje lotniska na podstawie przekazanego kryterium.
     * @param city miasto.
     * @param code kod lotniska.
     * @param country kraj.
     * @param name nazwa lotniska.
     * @return lotniska spełniające podane kryterium
     */
    List<AirportDto> find(String name, String code, String country, String city) throws AppBaseException;

    /**
     * Zwraca lotnisko o podanym identyfikatorze.
     * @param id identyfikator lotniska
     * @return lotnisko o podanym identyfikatorze
     * @throws AppBaseException w przypadku niepowodzenia operacji
     */
    AirportDto findById(Long id) throws AppBaseException;

    /**
     * Zwraca set z nazwami krajów dostępnych lotnisk.
     * @return set nazw krajów.
     * @throws AppBaseException
     */
    Set<String> getCountries() throws AppBaseException;

    /**
     * Zwraca set z nazwami miast dostępnych lotnisk.
     * @return set nazw miast
     * @throws AppBaseException
     */
    Set<String> getCities() throws AppBaseException;

    /**
     * Tworzy i zapisuje w bazie lotnisko.
     * @param airportDto dane nowego lotniska
     * @return stworzone lotnisko
     * @throws AppBaseException w przypadku niepowodzenia operacji
     */
    AirportDto create(AirportDto airportDto) throws AppBaseException;

    /**
     * Usuwa lotnisko o podanym identyfikatorze.
     * @param id identyfikator lotniska do usunięcia
     * @throws AppBaseException w przypadku niepowodzenia operacji
     */
    void delete(Long id) throws AppBaseException;

    /**
     * Modyfikuje istniejące lotnisko.
     * @param id identyfikator lotniska, które ma zostać zmodyfikowane
     * @param airportDto dane, które mają zostać zapisane
     * @throws AppBaseException w przypadku niepowodzenia operacji
     */
    void update(Long id, AirportDto airportDto) throws AppBaseException;
}

package pl.lodz.p.it.ssbd2020.ssbd04.mol.endpoints;

import pl.lodz.p.it.ssbd2020.ssbd04.common.TransactionStarter;
import pl.lodz.p.it.ssbd2020.ssbd04.exceptions.AppBaseException;
import pl.lodz.p.it.ssbd2020.ssbd04.mol.dto.AirportDto;

import javax.ejb.Local;
import java.util.List;

/**
 * Wykonuje konwersję klas DTO na model biznesowy
 * i jest granicą transakcji aplikacyjnej dla hierarchii klas Airport.
 */
@Local
public interface AirportEndpoint extends TransactionStarter {
    /**
     * Wyszukuje lotniska na podstawie przekazanego kryterium.
     *
     * @param city    miasto.
     * @param code    kod lotniska.
     * @param country kraj.
     * @param name    nazwa lotniska.
     * @return lotniska spełniające podane kryterium
     * @throws AppBaseException gdy operacja nie powiedzie się
     */
    List<AirportDto> find(String name, String code, String country, String city) throws AppBaseException;

    /**
     * Zwraca lotnisko o podanym identyfikatorze.
     *
     * @param id identyfikator lotniska
     * @return lotnisko o podanym identyfikatorze
     * @throws AppBaseException w przypadku niepowodzenia operacji
     */
    AirportDto findByCode(String id) throws AppBaseException;

    /**
     * Zwraca lotniska, których kod pasuje do podanej frazy.
     *
     * @param phrase fraza z kodem lotniska
     * @return lista lotnisk
     * @throws AppBaseException gdy operacja się nie powiedzie
     */
    List<AirportDto> findByMatchingCode(String phrase) throws AppBaseException;

    /**
     * Zwraca listę  z nazwami krajów dostępnych lotnisk.
     *
     * @return lista nazw krajów.
     * @throws AppBaseException gdy operacja się nie powiedzie
     */
    List<String> getCountries() throws AppBaseException;

    /**
     * Zwraca listę z nazwami miast dostępnych lotnisk.
     *
     * @return lista nazw miast
     * @throws AppBaseException gdy operacja się nie powiedzie
     */
    List<String> getCities() throws AppBaseException;

    /**
     * Tworzy i zapisuje w bazie lotnisko.
     *
     * @param airportDto dane nowego lotniska
     * @return stworzone lotnisko
     * @throws AppBaseException w przypadku niepowodzenia operacji
     */
    AirportDto create(AirportDto airportDto) throws AppBaseException;

    /**
     * Usuwa lotnisko o podanym identyfikatorze.
     *
     * @param code identyfikator lotniska do usunięcia
     * @throws AppBaseException w przypadku niepowodzenia operacji
     */
    void delete(String code) throws AppBaseException;

    /**
     * Modyfikuje istniejące lotnisko.
     *
     * @param code       identyfikator lotniska, które ma zostać zmodyfikowane
     * @param airportDto dane, które mają zostać zapisane
     * @throws AppBaseException w przypadku niepowodzenia operacji
     */
    void update(String code, AirportDto airportDto) throws AppBaseException;
}

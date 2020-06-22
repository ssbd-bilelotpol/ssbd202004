package pl.lodz.p.it.ssbd2020.ssbd04.mol.endpoints;

import pl.lodz.p.it.ssbd2020.ssbd04.common.TransactionStarter;
import pl.lodz.p.it.ssbd2020.ssbd04.exceptions.AppBaseException;
import pl.lodz.p.it.ssbd2020.ssbd04.mol.dto.ConnectionCreateDto;
import pl.lodz.p.it.ssbd2020.ssbd04.mol.dto.ConnectionDto;

import javax.ejb.Local;
import java.util.List;

/**
 * Wykonuje konwersję klas DTO na model biznesowy
 * i jest granicą transakcji aplikacyjnej dla hierarchii klas Connection.
 */
@Local
public interface ConnectionEndpoint extends TransactionStarter {
    /**
     * Wyszukuje połączenia pomiędzy lotniskami o danych kodach.
     *
     * @param destinationCode kod lotniska przylotu
     * @param sourceCode      kod lotniska wylotu
     * @return połączenia spełniające podane kryterium
     * @throws AppBaseException gdy operacja nie powiedzie się
     */
    List<ConnectionDto> find(String destinationCode, String sourceCode) throws AppBaseException;

    /**
     * Wszykuje połączenie na podstawie frazy.
     *
     * @param phrase fraza do szukania(np. WSZ - LDZ)
     * @return znalezione połączenia
     * @throws AppBaseException w przypadku niepowodzenia operacji
     */
    List<ConnectionDto> findByPhrase(String phrase) throws AppBaseException;


    /**
     * Zwraca połączenie o podanym identyfikatorze.
     *
     * @param id identyfikator połączenia
     * @return połączenie o podanym identyfikatorze
     * @throws AppBaseException w przypadku niepowodzenia operacji
     */
    ConnectionDto findById(Long id) throws AppBaseException;

    /**
     * Tworzy i zapisuje w bazie połączenie.
     *
     * @param connectionCreateDto dane nowego połączenia
     * @return stworzone połączenie
     * @throws AppBaseException w przypadku niepowodzenia operacji
     */
    ConnectionDto create(ConnectionCreateDto connectionCreateDto) throws AppBaseException;

    /**
     * Usuwa połączenie o podanym identyfikatorze.
     *
     * @param id identyfikator połączenia do usunięcia
     * @throws AppBaseException w przypadku niepowodzenia operacji
     */
    void delete(Long id) throws AppBaseException;

    /**
     * Modyfikuje istniejące połączenie.
     *
     * @param id            identyfikator połączenia, które ma zostać zmodyfikowane
     * @param connectionDto dane, które mają zostać zapisane
     * @throws AppBaseException w przypadku niepowodzenia operacji
     */
    void update(Long id, ConnectionCreateDto connectionDto) throws AppBaseException;
}

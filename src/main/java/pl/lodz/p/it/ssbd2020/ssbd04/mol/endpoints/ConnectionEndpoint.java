package pl.lodz.p.it.ssbd2020.ssbd04.mol.endpoints;

import pl.lodz.p.it.ssbd2020.ssbd04.common.TransactionStarter;
import pl.lodz.p.it.ssbd2020.ssbd04.exceptions.AppBaseException;
import pl.lodz.p.it.ssbd2020.ssbd04.mol.dto.ConnectionDto;
import pl.lodz.p.it.ssbd2020.ssbd04.mol.dto.ConnectionQueryDto;

import javax.ejb.Local;
import java.util.List;

/**
 * Wykonuje konwersję klas DTO na model biznesowy
 * i jest granicą transakcji aplikacyjnej dla hierarchii klas Connection.
 */
@Local
public interface ConnectionEndpoint extends TransactionStarter {
    /**
     * Wyszukuje połączenia na podstawie przekazanego kryterium.
     * @param query kryterium.
     * @return połączenia spełniające podane kryterium.
     */
    List<ConnectionDto> find(ConnectionQueryDto query);

    /**
     * Zwraca połączenie o podanym identyfikatorze.
     * @param id identyfikator połączenia.
     * @return połączenie o podanym identyfikatorze.
     * @throws AppBaseException w przypadku niepowodzenia operacji.
     */
    ConnectionDto findById(Long id) throws AppBaseException;

    /**
     * Zwraca połączenie o zgodnych lotniskach źródłowym i docelowym.
     * @param sourceCode kod lotniska wylotu.
     * @param destinationCode kot lotniska przylotu.
     * @return połączenie spełniające podane kryterium.
     * @throws AppBaseException
     */
    ConnectionDto findByAirports(String sourceCode, String destinationCode) throws AppBaseException;

    /**
     * Tworzy i zapisuje w bazie połączenie.
     * @param connectionDto dane nowego połączenia.
     * @return stworzone połączenie.
     * @throws AppBaseException w przypadku niepowodzenia operacji.
     */
    ConnectionDto create(ConnectionDto connectionDto) throws AppBaseException;

    /**
     * Usuwa połączenie o podanym identyfikatorze.
     * @param id identyfikator połączenia do usunięcia.
     * @throws AppBaseException w przypadku niepowodzenia operacji.
     */
    void delete(Long id) throws AppBaseException;

    /**
     * Modyfikuje istniejące połączenie.
     * @param id identyfikator połączenia, które ma zostać zmodyfikowane.
     * @param connectionDto dane, które mają zostać zapisane.
     * @throws AppBaseException w przypadku niepowodzenia operacji.
     */
    void update(Long id, ConnectionDto connectionDto) throws AppBaseException;
}

package pl.lodz.p.it.ssbd2020.ssbd04.mol.endpoints;

import pl.lodz.p.it.ssbd2020.ssbd04.common.TransactionStarter;
import pl.lodz.p.it.ssbd2020.ssbd04.exceptions.AppBaseException;
import pl.lodz.p.it.ssbd2020.ssbd04.mol.dto.ConnectionDto;
import pl.lodz.p.it.ssbd2020.ssbd04.mol.dto.ConnectionQueryDto;

import javax.ejb.Local;

/**
 * Wykonuje konwersję klas DTO na model biznesowy
 * i jest granicą transakcji aplikacyjnej dla hierarchii klas Connection.
 */
@Local
public interface ConnectionEndpoint extends TransactionStarter {
    /**
     * Wyszukuje połączenia na podstawie przekazanego kryterium.
     * @param query kryterium
     * @return połączenia spełniające podane kryterium
     */
    ConnectionDto find(ConnectionQueryDto query) throws AppBaseException;

    /**
     * Zwraca połączenie o podanym identyfikatorze.
     * @param id identyfikator połączenia
     * @return połączenie o podanym identyfikatorze
     * @throws AppBaseException w przypadku niepowodzenia operacji
     */
    ConnectionDto findById(Long id) throws AppBaseException;

    /**
     * Tworzy i zapisuje w bazie połączenie.
     * @param connectionDto dane nowego połączenia
     * @return stworzone połączenie
     * @throws AppBaseException w przypadku niepowodzenia operacji
     */
    ConnectionDto create(ConnectionDto connectionDto) throws AppBaseException;

    /**
     * Usuwa połączenie o podanym identyfikatorze.
     * @param id identyfikator połączenia do usunięcia
     * @throws AppBaseException w przypadku niepowodzenia operacji
     */
    void delete(Long id) throws AppBaseException;

    /**
     * Modyfikuje istniejące połączenie.
     * @param id identyfikator połączenia, które ma zostać zmodyfikowane
     * @param connectionDto dane, które mają zostać zapisane
     * @throws AppBaseException w przypadku niepowodzenia operacji
     */
    void update(Long id, ConnectionDto connectionDto) throws AppBaseException;
}

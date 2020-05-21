package pl.lodz.p.it.ssbd2020.ssbd04.mol.endpoints;

import pl.lodz.p.it.ssbd2020.ssbd04.exceptions.AppBaseException;
import pl.lodz.p.it.ssbd2020.ssbd04.mol.dto.ConnectionDto;
import pl.lodz.p.it.ssbd2020.ssbd04.mol.dto.ConnectionQueryDto;
import pl.lodz.p.it.ssbd2020.ssbd04.security.Role;

import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;
import javax.ejb.Local;
import java.util.List;

/**
 * Wykonuje konwersję klas DTO na model biznesowy
 * i jest granicą transakcji aplikacyjnej dla hierarchii klas Connection.
 */
@Local
public interface ConnectionEndpoint {
    /**
     * Wyszukuje połączenia na podstawie przekazanego kryterium.
     * @param query kryterium
     * @return połączenia spełniające podane kryterium
     */
    @PermitAll
    List<ConnectionDto> find(ConnectionQueryDto query);

    /**
     * Zwraca połączenie o podanym identyfikatorze.
     * @param id identyfikator połączenia
     * @return połączenie o podanym identyfikatorze
     * @throws AppBaseException w przypadku niepowodzenia operacji
     */
    @PermitAll
    ConnectionDto findById(Long id) throws AppBaseException;

    /**
     * Tworzy i zapisuje w bazie połączenie.
     * @param connectionDto dane nowego połączenia
     * @return stworzone połączenie.
     * @throws AppBaseException w przypadku niepowodzenia operacji
     */
    @RolesAllowed(Role.CreateConnection)
    ConnectionDto create(ConnectionDto connectionDto) throws AppBaseException;

    /**
     * Usuwa połączenie o podanym identyfikatorze.
     * @param id identyfikator połączenia do usunięcia
     * @throws AppBaseException w przypadku niepowodzenia operacji
     */
    @RolesAllowed(Role.DeleteConnection)
    void delete(Long id) throws AppBaseException;

    /**
     * Modyfikuje istniejące połączenie.
     * @param id identyfikator połączenia, które ma zostać zmodyfikowane
     * @param connectionDto dane, które mają zostać zapisane
     * @throws AppBaseException w przypadku niepowodzenia operacji
     */
    @RolesAllowed(Role.UpdateConnection)
    void update(Long id, ConnectionDto connectionDto) throws AppBaseException;
}

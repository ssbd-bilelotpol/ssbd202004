package pl.lodz.p.it.ssbd2020.ssbd04.mob.endpoints;

import pl.lodz.p.it.ssbd2020.ssbd04.common.TransactionStarter;
import pl.lodz.p.it.ssbd2020.ssbd04.exceptions.AppBaseException;
import pl.lodz.p.it.ssbd2020.ssbd04.mob.dto.*;
import pl.lodz.p.it.ssbd2020.ssbd04.mol.dto.SeatDto;

import javax.ejb.Local;
import java.time.LocalDateTime;
import java.util.List;

/**
 * Wykonuje konwersję klas DTO na model biznesowy
 * i jest granicą transakcji aplikacyjnej dla hierarchii klas Ticket.
 */
@Local
public interface TicketEndpoint extends TransactionStarter {

    /**
     * Zwraca bilet o określonym ID
     *
     * @param id identyfikator biletu
     * @return bilet o określonym ID
     * @throws AppBaseException gdy nie powiedzie się pobieranie informacji o bilecie
     */
    TicketDto findById(Long id) throws AppBaseException;

    /**
     * Zwraca bilety dla wybranego lotu
     *
     * @param id identyfikator lotu
     * @return bilety dla wybranego lotu
     * @throws AppBaseException gdy nie powiedzie się pobieranie listy biletów
     */
    List<TicketDto> findByFlight(Long id) throws AppBaseException;

    /**
     * Zwraca bilety danego użytkownika
     *
     * @param id identyfikator użytkownika
     * @return bilety danego użytkownika
     * @throws AppBaseException gdy nie powiedzie się pobieranie listy biletów
     */
    List<TicketDto> findByAccount(Long id) throws AppBaseException;

    /**
     * Zwraca listę wszystkich biletów
     *
     * @return lista wszystkich biletów
     * @throws AppBaseException gdy nie powiedzie się zwrócenie listy biletów
     */
    List<TicketDto> getAllTickets() throws AppBaseException;

    /**
     * Zwraca listę biletów aktualnie zalogowanego użytkownika
     *
     * @return lista biletów aktualnie zalogowanego użytkownika
     * @throws AppBaseException gdy nie powiedzie się zwrócenie listy biletów
     */
    List<TicketDto> getOwnTickets() throws AppBaseException;

    /**
     * Tworzy bilet o wybranych parametrach
     *
     * @param ticketDto parametry kupowanego biletu
     * @throws AppBaseException gdy nie powiedzie się tworzenie nowego biletu
     */
    void buyTicket(TicketBuyDto ticketDto) throws AppBaseException;

    /**
     * Zwraca zakupiony bilet
     *
     * @param id identyfikator zwracanego biletu
     * @throws AppBaseException gdy nie powiedzie się zwracanie biletu
     */
    void returnTicket(Long id) throws AppBaseException;

    /**
     * Aktualizuje dane biletu
     *
     * @param id              identyfikator biletu
     * @param ticketUpdateDto nowe dane biletu
     * @throws AppBaseException gdy nie powiedzie się aktualizacja biletu
     */
    void update(Long id, TicketUpdateDto ticketUpdateDto) throws AppBaseException;

    /**
     * Wyszukuje listę bilety na podstawie przekazanego kryterium.
     *
     * @param code kod lotu
     * @param connectionId id połączenia
     * @param airplaneId id lotniska
     * @param from data, po której wylatuje lot
     * @param to dat, przed którą wylatuje lot
     * @return loty spełniające podane kryterium
     */
    List<TicketListDto> find(String code, Long connectionId, Long airplaneId,
                             LocalDateTime from, LocalDateTime to) throws AppBaseException;

    /**
     * Zwraca listę z informacjami o zyskach na danych połączeniach
     * @return lista z informacjami o zyskach na danych połączeniach
     * @throws AppBaseException gdy nie będzie połączenia z bazą danych
     */
    List<ReportDto> generateReport() throws AppBaseException;

}

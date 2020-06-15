package pl.lodz.p.it.ssbd2020.ssbd04.mol.endpoints;

import pl.lodz.p.it.ssbd2020.ssbd04.common.TransactionStarter;
import pl.lodz.p.it.ssbd2020.ssbd04.exceptions.AppBaseException;
import pl.lodz.p.it.ssbd2020.ssbd04.mol.dto.FlightCreateDto;
import pl.lodz.p.it.ssbd2020.ssbd04.mol.dto.FlightDto;
import pl.lodz.p.it.ssbd2020.ssbd04.mol.dto.SeatDto;

import javax.ejb.Local;
import java.time.LocalDateTime;
import java.util.List;

@Local
public interface FlightEndpoint extends TransactionStarter {
    /**
     * Wyszukuje loty na podstawie przekazanego kryterium.
     * @param code kod lotu
     * @param connectionId id połączenia
     * @param airplaneId id lotniska
     * @param from data, po której wylatuje lot
     * @param to dat, przed którą wylatuje lot
     * @return loty spełniające podane kryterium
     */
    List<FlightDto> find(String code, Long connectionId, Long airplaneId, LocalDateTime from, LocalDateTime to)
            throws AppBaseException;

    /**
     * Zwraca loty o podanym identyfikatorze.
     * @param code identyfikator lotu
     * @return lot o podanym identyfikatorze
     * @throws AppBaseException w przypadku niepowodzenia operacji
     */
    FlightDto findByCode(String code) throws AppBaseException;

    List<SeatDto> getTakenSeats(Long id) throws AppBaseException;

    /**
     * Tworzy i zapisuje w bazie lot.
     * @param flightDto dane nowego lotu
     * @return stworzony lot
     * @throws AppBaseException w przypadku niepowodzenia operacji
     */
    FlightDto create(FlightCreateDto flightDto) throws AppBaseException;

    /**
     * Anuluje lot o podanym identyfikatorze.
     * @param id identyfikator lotu do anulowania
     * @throws AppBaseException w przypadku niepowodzenia operacji
     */
    void cancel(Long id) throws AppBaseException;

    /**
     * Modyfikuje istniejący lot.
     * @param id identyfikator lotu, który ma zostać zmodyfikowany
     * @param flightDto dane, które mają zostać zapisane
     * @throws AppBaseException w przypadku niepowodzenia operacji
     */
    void update(Long id, FlightDto flightDto) throws AppBaseException;
}

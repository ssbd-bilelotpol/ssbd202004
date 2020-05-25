package pl.lodz.p.it.ssbd2020.ssbd04.mol.endpoints;

import pl.lodz.p.it.ssbd2020.ssbd04.exceptions.AppBaseException;
import pl.lodz.p.it.ssbd2020.ssbd04.mol.dto.FlightDto;
import pl.lodz.p.it.ssbd2020.ssbd04.mol.dto.FlightQueryDto;
import pl.lodz.p.it.ssbd2020.ssbd04.mol.dto.SeatDto;

import javax.ejb.Local;
import java.util.List;

@Local
public interface FlightEndpoint {
    /**
     * Wyszukuje loty na podstawie przekazanego kryterium.
     * @param query kryterium
     * @return loty spełniające podane kryterium
     */
    List<FlightDto> find(FlightQueryDto query);

    /**
     * Zwraca loty o podanym identyfikatorze.
     * @param id identyfikator lotu
     * @return lot o podanym identyfikatorze
     * @throws AppBaseException w przypadku niepowodzenia operacji
     */
    FlightDto findById(Long id) throws AppBaseException;

    List<SeatDto> getTakenSeats(Long id) throws AppBaseException;

    /**
     * Tworzy i zapisuje w bazie lot.
     * @param flightDto dane nowego lotu
     * @return stworzony lot
     * @throws AppBaseException w przypadku niepowodzenia operacji
     */
    FlightDto create(FlightDto flightDto) throws AppBaseException;

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

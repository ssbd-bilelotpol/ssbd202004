package pl.lodz.p.it.ssbd2020.ssbd04.mol.endpoints;

import pl.lodz.p.it.ssbd2020.ssbd04.common.TransactionStarter;
import pl.lodz.p.it.ssbd2020.ssbd04.exceptions.AppBaseException;
import pl.lodz.p.it.ssbd2020.ssbd04.mol.dto.BenefitDto;
import pl.lodz.p.it.ssbd2020.ssbd04.mol.dto.SeatClassDto;

import javax.ejb.Local;
import java.util.List;

/**
 * Wykonuje konwersję klas DTO na model biznesowy
 * i jest granicą transakcji aplikacyjnej dla hierarchii klas SeatClass.
 */
@Local
public interface SeatClassEndpoint extends TransactionStarter {
    /**
     * Znajduje klasę miejsc na podstawie jej nazwy.
     *
     * @param name nazwa klasy miejsc.
     * @return dane klasy miejsc.
     * @throws AppBaseException gdy operacja nie powiedzie się, bądź klasa miejsc nie istnieje.
     */
    SeatClassDto findByName(String name) throws AppBaseException;

    /**
     * Zwraca wszystkie dostępne dodatki, które mogą zostać przypisane do klas miejsc.
     *
     * @return listę wszystkich dodatków.
     */
    List<BenefitDto> getAllBenefits();

    /**
     * Zwraca wszystkie dostępne klasy miejsc, które mogą zostać przypisane do siedzeń.
     *
     * @return listę wszystkich klas miejsc.
     */
    List<SeatClassDto> getAll();

    /**
     * Tworzy nową klasę miejsc.
     *
     * @param seatClassDto dane klasy miejsc.
     * @return utworzoną klasę miejsc.
     * @throws AppBaseException gdy nazwa klasy miejsc jest już zajęta, bądź operacja nie powiodła się.
     */
    SeatClassDto create(SeatClassDto seatClassDto) throws AppBaseException;

    /**
     * Usuwa klasę miejsc.
     *
     * @param name nazwa klasy miejsc.
     * @throws AppBaseException gdy klasa miejsc nie istnieje, jest używana przez siedzenie, bądź operacja nie powiodła się.
     */
    void delete(String name) throws AppBaseException;

    /**
     * Aktualizuję klasę miejsc.
     *
     * @param seatClassDto dane klasy miejsc.
     * @throws AppBaseException gdy wystąpił problem konkurencyjnej modyfikacji, klasa miejsc nie istnieje, bądź operacja nie powiodła się.
     */
    void update(SeatClassDto seatClassDto) throws AppBaseException;
}

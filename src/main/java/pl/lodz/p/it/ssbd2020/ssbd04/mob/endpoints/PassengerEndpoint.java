package pl.lodz.p.it.ssbd2020.ssbd04.mob.endpoints;

import pl.lodz.p.it.ssbd2020.ssbd04.common.TransactionStarter;
import pl.lodz.p.it.ssbd2020.ssbd04.exceptions.AppBaseException;
import pl.lodz.p.it.ssbd2020.ssbd04.mob.dto.*;

import javax.ejb.Local;
import java.util.List;

/**
 * Wykonuje konwersję klas DTO na model biznesowy
 * i jest granicą transakcji aplikacyjnej dla hierarchii klas Ticket.
 */
@Local
public interface PassengerEndpoint extends TransactionStarter {

    /**
     * Wyszukuje pasażerów na podstawie przekazanego kryterium.
     * @param flightCode kod lotniska.
     * @param name imię i nazwisko pasażera.
     * @return lotniska spełniające podane kryterium.
     */
    List<PassengerDto> find(String name, String flightCode) throws AppBaseException;
}

package pl.lodz.p.it.ssbd2020.ssbd04.mob.services;

import pl.lodz.p.it.ssbd2020.ssbd04.entities.Account;
import pl.lodz.p.it.ssbd2020.ssbd04.entities.Connection;
import pl.lodz.p.it.ssbd2020.ssbd04.entities.Flight;
import pl.lodz.p.it.ssbd2020.ssbd04.entities.Ticket;
import pl.lodz.p.it.ssbd2020.ssbd04.exceptions.AppBaseException;
import pl.lodz.p.it.ssbd2020.ssbd04.interceptors.TrackingInterceptor;
import pl.lodz.p.it.ssbd2020.ssbd04.mob.dto.ReportDto;
import pl.lodz.p.it.ssbd2020.ssbd04.mob.dto.TicketReturnDto;
import pl.lodz.p.it.ssbd2020.ssbd04.mob.facades.ConnectionFacade;
import pl.lodz.p.it.ssbd2020.ssbd04.mob.facades.TicketFacade;
import pl.lodz.p.it.ssbd2020.ssbd04.security.Role;

import javax.annotation.security.RolesAllowed;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;
import javax.interceptor.Interceptors;
import java.util.List;

/**
 * Przetwarzanie logiki biznesowej biletów.
 */
@Interceptors({TrackingInterceptor.class})
@Stateless
@TransactionAttribute(TransactionAttributeType.MANDATORY)
public class TicketService {

    @Inject
    private TicketFacade ticketFacade;

    @Inject
    private ConnectionFacade connectionFacade;

    /**
     * Zwraca bilet o określonym ID
     *
     * @param id identyfikator biletu
     * @return bilet o określonym ID
     * @throws AppBaseException gdy nie powiedzie się pobieranie informacji o bilecie
     */
    @RolesAllowed(Role.FindTicketById)
    public Ticket findById(Long id) throws AppBaseException {
        return ticketFacade.find(id);
    }

    /**
     * Zwraca bilety dla wybranego lotu
     *
     * @param flight lot
     * @return bilety dla wybranego lotu
     * @throws AppBaseException gdy nie powiedzie się pobieranie listy biletów
     */
    @RolesAllowed(Role.FindTicketsByFlight)
    public List<Ticket> findByFlight(Flight flight) throws AppBaseException {
        throw new UnsupportedOperationException();
    }

    /**
     * Zwraca bilety danego użytkownika
     *
     * @param account konto użytkownika
     * @return bilety danego użytkownika
     * @throws AppBaseException gdy nie powiedzie się pobieranie listy biletów
     */
    @RolesAllowed(Role.FindTicketsByAccount)
    public List<Ticket> findByAccount(Account account) throws AppBaseException {
        return ticketFacade.findByAccount(account.getId());
    }

    /**
     * Zwraca listę wszystkich biletów
     *
     * @return lista wszystkich biletów
     * @throws AppBaseException gdy nie powiedzie się zwrócenie listy biletów
     */
    @RolesAllowed(Role.GetAllTickets)
    public List<Ticket> getAllTickets() throws AppBaseException {
        throw new UnsupportedOperationException();
    }

    /**
     * Tworzy bilet o wybranych parametrach
     *
     * @param ticket parametry kupowanego biletu
     * @throws AppBaseException gdy nie powiedzie się tworzenie nowego biletu
     */
    @RolesAllowed(Role.CreateTicket)
    public void buyTicket(Ticket ticket) throws AppBaseException {
        Connection connection = ticket.getFlight().getConnection();
        connection.setProfit(ticket.getTotalPrice().add(connection.getProfit()));
        connectionFacade.edit(connection);
        ticketFacade.create(ticket);
    }

    /**
     * Zwraca zakupiony bilet
     *
     * @param ticket parametry zwracania biletu
     * @throws AppBaseException gdy nie powiedzie się zwracanie biletu
     */
    @RolesAllowed(Role.ReturnTicket)
    public void returnTicket(Ticket ticket) throws AppBaseException {
        Connection connection = ticket.getFlight().getConnection();
        connection.setProfit(connection.getProfit().subtract(ticket.getTotalPrice()));
        connectionFacade.edit(connection);
        throw new UnsupportedOperationException();
    }

    /**
     * Aktualizuje dane biletu
     *
     * @param ticket nowe dane biletu
     * @throws AppBaseException gdy nie powiedzie się aktualizacja biletu
     */
    @RolesAllowed(Role.UpdateTicket)
    public void update(Ticket ticket) throws AppBaseException {
        throw new UnsupportedOperationException();
    }
}

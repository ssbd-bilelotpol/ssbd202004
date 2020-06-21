package pl.lodz.p.it.ssbd2020.ssbd04.mob.services;

import pl.lodz.p.it.ssbd2020.ssbd04.entities.Account;
import pl.lodz.p.it.ssbd2020.ssbd04.entities.Connection;
import pl.lodz.p.it.ssbd2020.ssbd04.entities.Ticket;
import pl.lodz.p.it.ssbd2020.ssbd04.exceptions.AppBaseException;
import pl.lodz.p.it.ssbd2020.ssbd04.interceptors.TrackingInterceptor;
import pl.lodz.p.it.ssbd2020.ssbd04.mob.facades.ConnectionFacade;
import pl.lodz.p.it.ssbd2020.ssbd04.mob.facades.TicketFacade;
import pl.lodz.p.it.ssbd2020.ssbd04.security.Role;

import javax.annotation.security.RolesAllowed;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;
import javax.interceptor.Interceptors;
import java.util.ArrayList;
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
     * Zwraca bilety danego użytkownika
     *
     * @param account konto użytkownika
     * @return bilety danego użytkownika
     * @throws AppBaseException gdy nie powiedzie się pobieranie listy biletów
     */
    @RolesAllowed({Role.FindTicketsByAccount, Role.GetOwnTickets})
    public List<Ticket> findByAccount(Account account) throws AppBaseException {
        return ticketFacade.findByAccount(account.getId());
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
        ticketFacade.remove(ticket);
    }

    /**
     * Aktualizuje dane biletu
     *
     * @param ticket nowe dane biletu
     * @throws AppBaseException gdy nie powiedzie się aktualizacja biletu
     */
    @RolesAllowed(Role.UpdateTicket)
    public void update(Ticket ticket) throws AppBaseException {
        ticketFacade.edit(ticket);
    }

    /**
     * Wyszukuje bilety przypisane do danych lotów
     * @param flightIds lista lotów, dla których ma znaleźć bilety
     * @return lista znalezionych lotów
     */
    @RolesAllowed(Role.FindTicketsByFlights)
    public List<Ticket> findByFlights(List<Long> flightIds) {
        if (flightIds.size() == 0) return new ArrayList<>();
        return ticketFacade.findByFlights(flightIds);
    }
}

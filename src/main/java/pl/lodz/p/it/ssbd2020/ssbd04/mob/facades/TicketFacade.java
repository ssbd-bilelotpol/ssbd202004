package pl.lodz.p.it.ssbd2020.ssbd04.mob.facades;

import org.hibernate.exception.ConstraintViolationException;
import pl.lodz.p.it.ssbd2020.ssbd04.common.AbstractFacade;
import pl.lodz.p.it.ssbd2020.ssbd04.entities.Airport;
import pl.lodz.p.it.ssbd2020.ssbd04.entities.Ticket;
import pl.lodz.p.it.ssbd2020.ssbd04.exceptions.AirportException;
import pl.lodz.p.it.ssbd2020.ssbd04.exceptions.AppBaseException;
import pl.lodz.p.it.ssbd2020.ssbd04.exceptions.TicketException;
import pl.lodz.p.it.ssbd2020.ssbd04.interceptors.TrackingInterceptor;
import pl.lodz.p.it.ssbd2020.ssbd04.security.Role;

import javax.annotation.security.RolesAllowed;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.interceptor.Interceptors;
import javax.persistence.EntityManager;
import javax.persistence.LockModeType;
import javax.persistence.PersistenceContext;
import java.util.List;

@Interceptors({TrackingInterceptor.class})
@Stateless
@TransactionAttribute(TransactionAttributeType.MANDATORY)
public class TicketFacade extends AbstractFacade<Ticket> {

    @PersistenceContext(unitName = "ssbd04mobPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public TicketFacade() {
        super(Ticket.class);
    }

    /**
     * Zwraca bilet o określonym ID
     *
     * @param id identyfikator biletu
     * @return bilet o określonym ID
     * @throws AppBaseException gdy nie powiedzie się pobieranie informacji o bilecie
     */
    @RolesAllowed(Role.FindTicketById)
    public Ticket find(Long id) throws AppBaseException {
        throw new UnsupportedOperationException();
    }

    /**
     * Zwraca bilety dla wybranego lotu
     *
     * @param flightId identyfikator lotu
     * @return bilety dla wybranego lotu
     * @throws AppBaseException gdy nie powiedzie się pobieranie listy biletów
     */
    @RolesAllowed(Role.FindTicketsByFlight)
    public Ticket findByFlight(Long flightId) throws AppBaseException {
        throw new UnsupportedOperationException();
    }

    /**
     * Zwraca bilety danego użytkownika
     *
     * @param accountId identyfikator użytkownika
     * @return bilety danego użytkownika
     * @throws AppBaseException gdy nie powiedzie się pobieranie listy biletów
     */
    @RolesAllowed(Role.FindTicketsByAccount)
    public List<Ticket> findByAccount(Long accountId) throws AppBaseException {
        throw new UnsupportedOperationException();
    }

    /**
     * Zwraca listę wszystkich biletów
     *
     * @return lista wszystkich biletów
     * @throws AppBaseException gdy nie powiedzie się zwrócenie listy biletów
     */
    @RolesAllowed(Role.GetAllTickets)
    public List<Ticket> findAll() throws AppBaseException {
        throw new UnsupportedOperationException();
    }

    /**
     * Tworzy bilet o wybranych parametrach
     *
     * @param entity parametry kupowanego biletu
     * @throws AppBaseException gdy nie powiedzie się tworzenie nowego biletu
     */
    @RolesAllowed(Role.CreateTicket)
    public void create(Ticket entity) throws AppBaseException {
        try {
            super.create(entity);
            em.lock(entity.getFlight(), LockModeType.OPTIMISTIC_FORCE_INCREMENT);
        } catch (ConstraintViolationException e) {
            if (e.getCause().getMessage().contains(Ticket.CONSTRAINT_SEAT_TAKEN)) {
                throw TicketException.seatTaken();
            }
            throw AppBaseException.databaseOperation(e);
        }
    }

    /**
     * Zwraca zakupiony bilet
     *
     * @param ticket identyfikator biletu
     * @throws AppBaseException gdy nie powiedzie się zwracanie biletu
     */
    @RolesAllowed(Role.ReturnTicket)
    public void remove(Ticket ticket) throws AppBaseException {
        throw new UnsupportedOperationException();
    }

    /**
     * Aktualizuje dane biletu
     *
     * @param ticket nowe dane biletu
     * @throws AppBaseException gdy nie powiedzie się aktualizacja biletu
     */
    @RolesAllowed(Role.UpdateTicket)
    public void edit(Ticket ticket) throws AppBaseException {
        throw new UnsupportedOperationException();
    }

}

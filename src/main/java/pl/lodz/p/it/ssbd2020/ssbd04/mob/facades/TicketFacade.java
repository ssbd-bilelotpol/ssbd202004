package pl.lodz.p.it.ssbd2020.ssbd04.mob.facades;

import org.hibernate.exception.ConstraintViolationException;
import pl.lodz.p.it.ssbd2020.ssbd04.common.AbstractFacade;
import pl.lodz.p.it.ssbd2020.ssbd04.entities.Ticket;
import pl.lodz.p.it.ssbd2020.ssbd04.exceptions.AppBaseException;
import pl.lodz.p.it.ssbd2020.ssbd04.exceptions.TicketException;
import pl.lodz.p.it.ssbd2020.ssbd04.interceptors.TrackingInterceptor;
import pl.lodz.p.it.ssbd2020.ssbd04.security.Role;

import javax.annotation.security.RolesAllowed;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.interceptor.Interceptors;
import javax.persistence.*;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
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
    @RolesAllowed({Role.FindTicketById, Role.FindAnyTicketById})
    public Ticket find(Long id) throws AppBaseException {
        try {
            TypedQuery<Ticket> query = em.createNamedQuery("Ticket.findById", Ticket.class);
            query.setParameter("id", id);
            return query.getSingleResult();
        } catch (NoResultException e) {
            throw TicketException.notFound();
        } catch (PersistenceException e) {
            throw AppBaseException.databaseOperation(e);
        }
    }

    /**
     * Zwraca bilety danego użytkownika
     *
     * @param accountId identyfikator użytkownika
     * @return bilety danego użytkownika
     * @throws AppBaseException gdy nie powiedzie się pobieranie listy biletów
     */
    @RolesAllowed({Role.FindTicketsByAccount, Role.GetOwnTickets})
    public List<Ticket> findByAccount(Long accountId) throws AppBaseException {
        try {
            TypedQuery<Ticket> query = em.createNamedQuery("Ticket.findByAccount", Ticket.class);
            query.setParameter("accountId", accountId);
            return query.getResultList();
        } catch (PersistenceException e) {
            throw AppBaseException.databaseOperation(e);
        }
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
        super.remove(ticket);
    }

    /**
     * Aktualizuje dane biletu
     *
     * @param ticket nowe dane biletu
     * @throws AppBaseException gdy nie powiedzie się aktualizacja biletu
     */
    @RolesAllowed(Role.UpdateTicket)
    public void edit(Ticket ticket) throws AppBaseException {
        em.lock(ticket, LockModeType.OPTIMISTIC_FORCE_INCREMENT);
        super.edit(ticket);
    }

    /**
     * Zwraca bilety dla wybranych lotów
     *
     * @param flightIds identyfikator lotów
     * @return bilety dla wybranych lotów
     */
    @RolesAllowed(Role.FindTicketsByFlights)
    public List<Ticket> findByFlights(List<Long> flightIds) {
        CriteriaBuilder builder = em.getCriteriaBuilder();
        CriteriaQuery<Ticket> query = builder.createQuery(Ticket.class);
        Root<Ticket> root = query.from(Ticket.class);

        CriteriaBuilder.In<Long> inClause = builder.in(root.get("flight"));
        for (Long id : flightIds) {
            inClause.value(id);
        }
        query.select(root).where(inClause);

        TypedQuery<Ticket> typedQuery = em.createQuery(query);
        return typedQuery.getResultList();
    }
}

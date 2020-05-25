package pl.lodz.p.it.ssbd2020.ssbd04.mol.facades;

import pl.lodz.p.it.ssbd2020.ssbd04.common.AbstractFacade;
import pl.lodz.p.it.ssbd2020.ssbd04.entities.Flight;
import pl.lodz.p.it.ssbd2020.ssbd04.entities.Ticket;
import pl.lodz.p.it.ssbd2020.ssbd04.exceptions.AppBaseException;
import pl.lodz.p.it.ssbd2020.ssbd04.interceptors.TrackingInterceptor;

import javax.annotation.security.RolesAllowed;
import javax.ejb.Stateless;
import javax.interceptor.Interceptors;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

import static pl.lodz.p.it.ssbd2020.ssbd04.security.Role.GetTakenSeats;


@Interceptors({TrackingInterceptor.class})
@Stateless
public class TicketFacade extends AbstractFacade<Ticket> {

    @PersistenceContext(unitName = "ssbd04molPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public TicketFacade() {
        super(Ticket.class);
    }

    @RolesAllowed(GetTakenSeats)
    public List<Flight> findByFlight(Flight flight) throws AppBaseException {
        throw new UnsupportedOperationException();
    }
}

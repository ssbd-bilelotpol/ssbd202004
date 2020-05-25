package pl.lodz.p.it.ssbd2020.ssbd04.mob.endpoints;

import pl.lodz.p.it.ssbd2020.ssbd04.common.AbstractEndpoint;
import pl.lodz.p.it.ssbd2020.ssbd04.exceptions.AppBaseException;
import pl.lodz.p.it.ssbd2020.ssbd04.interceptors.TrackingInterceptor;
import pl.lodz.p.it.ssbd2020.ssbd04.mob.dto.TicketBuyDto;
import pl.lodz.p.it.ssbd2020.ssbd04.mob.dto.TicketDto;
import pl.lodz.p.it.ssbd2020.ssbd04.mob.dto.TicketReturnDto;
import pl.lodz.p.it.ssbd2020.ssbd04.mob.dto.TicketUpdateDto;
import pl.lodz.p.it.ssbd2020.ssbd04.mob.services.TicketService;
import pl.lodz.p.it.ssbd2020.ssbd04.security.Role;

import javax.annotation.security.RolesAllowed;
import javax.ejb.Stateful;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;
import javax.interceptor.Interceptors;
import java.util.List;

/**
 * Wykonuje konwersję klas DTO na model biznesowy
 * i jest granicą transakcji aplikacyjnej dla hierarchii klas Ticket.
 */
@Interceptors({TrackingInterceptor.class})
@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
@Stateful
public class TicketEndpointImpl extends AbstractEndpoint implements TicketEndpoint {

    @Inject
    private TicketService ticketService;

    @Override
    @RolesAllowed(Role.FindTicketById)
    public TicketDto findById(Long id) throws AppBaseException {
        throw new UnsupportedOperationException();
    }

    @Override
    @RolesAllowed(Role.FindTicketsByFlight)
    public List<TicketDto> findByFlight(Long id) throws AppBaseException {
        throw new UnsupportedOperationException();
    }

    @Override
    @RolesAllowed(Role.FindTicketsByAccount)
    public List<TicketDto> findByAccount(Long id) throws AppBaseException {
        return null;
    }

    @Override
    @RolesAllowed(Role.GetAllTickets)
    public List<TicketDto> getAllTickets() throws AppBaseException {
        throw new UnsupportedOperationException();
    }

    @Override
    @RolesAllowed(Role.GetOwnTickets)
    public List<TicketDto> getOwnTickets() throws AppBaseException {
        throw new UnsupportedOperationException();
    }

    @Override
    @RolesAllowed(Role.CreateTicket)
    public void buyTicket(TicketBuyDto ticketDto) throws AppBaseException {
        throw new UnsupportedOperationException();
    }

    @Override
    @RolesAllowed(Role.ReturnTicket)
    public void returnTicket(TicketReturnDto ticketDto) throws AppBaseException {
        throw new UnsupportedOperationException();
    }

    @Override
    @RolesAllowed(Role.UpdateTicket)
    public void update(Long id, TicketUpdateDto ticketUpdateDto) throws AppBaseException {
        throw new UnsupportedOperationException();
    }

}

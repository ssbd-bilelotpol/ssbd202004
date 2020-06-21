package pl.lodz.p.it.ssbd2020.ssbd04.exceptions;

import pl.lodz.p.it.ssbd2020.ssbd04.entities.Ticket;

import static pl.lodz.p.it.ssbd2020.ssbd04.common.I18n.*;

public class TicketException extends AppBaseException {

    private final Ticket ticket;

    protected TicketException(String message, Throwable cause) {
        super(message, cause);
        this.ticket = null;
    }

    protected TicketException(String message, Throwable cause, Ticket ticket) {
        super(message, cause);
        this.ticket = ticket;
    }

    protected TicketException(String message) {
        super(message);
        this.ticket = null;
    }

    protected TicketException(String message, Ticket ticket) {
        super(message);
        this.ticket = ticket;
    }

    public static TicketException notFound() {
        return new TicketException(TICKET_NOT_FOUND);
    }

    public static TicketException seatTaken() {
        return new TicketException(TICKET_SEAT_TAKEN);
    }

    public static TicketException timeToReturnExpired() {
        return new TicketException(TICKET_RETURN_TIME_EXPIRED);
    }

}

package pl.lodz.p.it.ssbd2020.ssbd04.exceptions;

import pl.lodz.p.it.ssbd2020.ssbd04.entities.Ticket;

import static pl.lodz.p.it.ssbd2020.ssbd04.common.I18n.*;

/**
 * Wyjątek odpowiadający hierarchii klas Ticket
 */
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

    /**
     * Wyjątek reprezentujący próbę wykonania operacji na nieistniejącym bilecie
     *
     * @return wyjątek typu TicketException
     */
    public static TicketException notFound() {
        return new TicketException(TICKET_NOT_FOUND);
    }

    /**
     * Wyjątek reprezentujący próbę kupno biletu na zajęte miejsce
     *
     * @return wyjątek typu TicketException
     */
    public static TicketException seatTaken() {
        return new TicketException(TICKET_SEAT_TAKEN);
    }

    /**
     * Wyjątek reprezentujący próbę zwrotu biletu po dozwolonym czasie
     *
     * @return wyjątek typu TicketException
     */
    public static TicketException timeToReturnExpired() {
        return new TicketException(TICKET_RETURN_TIME_EXPIRED);
    }

}

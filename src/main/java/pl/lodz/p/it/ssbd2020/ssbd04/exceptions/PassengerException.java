package pl.lodz.p.it.ssbd2020.ssbd04.exceptions;

import pl.lodz.p.it.ssbd2020.ssbd04.entities.Passenger;

import static pl.lodz.p.it.ssbd2020.ssbd04.common.I18n.PASSENGER_NOT_FOUND;

/**
 * Wyjątek odpowiadający hierarchii klas Passenger.
 */
public class PassengerException extends AppBaseException {
    private final Passenger passenger;

    private PassengerException(String message) {
        super(message);
        this.passenger = null;
    }

    private PassengerException(String message, Throwable cause, Passenger passenger) {
        super(message, cause);
        this.passenger = passenger;
    }

    private PassengerException(String message, Passenger passenger) {
        super(message);
        this.passenger = passenger;
    }

    private PassengerException(String message, Throwable cause) {
        super(message, cause);
        this.passenger = null;
    }

    /**
     * Tworzy wyjątek reprezentujący brak pasażera.
     *
     * @return wyjątek reprezentujący brak pasażera.
     */
    public static PassengerException notFound() {
        return new PassengerException(PASSENGER_NOT_FOUND);
    }

}

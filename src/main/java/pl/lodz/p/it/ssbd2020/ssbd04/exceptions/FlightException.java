package pl.lodz.p.it.ssbd2020.ssbd04.exceptions;


import pl.lodz.p.it.ssbd2020.ssbd04.entities.Flight;

import static pl.lodz.p.it.ssbd2020.ssbd04.common.I18n.*;

/**
 * Wyjątek odpowiadający hierarchii klas Flight.
 */
public class FlightException extends AppBaseException {
    private final Flight fiight;

    private FlightException(String message) {
        super(message);
        this.fiight = null;
    }

    private FlightException(String message, Throwable cause, Flight Flight) {
        super(message, cause);
        this.fiight = Flight;
    }

    private FlightException(String message, Flight Flight) {
        super(message);
        this.fiight = Flight;
    }

    private FlightException(String message, Throwable cause) {
        super(message, cause);
        this.fiight = null;
    }

    /**
     * Tworzy wyjątek reprezentujący sytuację kiedy tworzony lot już istnieje.
     * @return stworzony wyjątek
     */
    public static FlightException exists() {
        return new FlightException(FLIGHT_EXISTS);
    }

    /**
     * Tworzy wyjątek reprezentujący brak lotu w bazie.
     * @return stworzony wyjątek
     */
    public static FlightException notFound() {
        return new FlightException(FLIGHT_NOT_FOUND);
    }
}
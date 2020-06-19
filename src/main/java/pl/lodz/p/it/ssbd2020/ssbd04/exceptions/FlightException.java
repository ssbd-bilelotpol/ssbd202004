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
     * Tworzy wyjątek reprezentujący sytuację kiedy lot już się odbył
     * @return stworzony wyjątek
     */
    public static FlightException alreadyDeparted() {
        return new FlightException(FLIGHT_DEPARTED);
    }

    /**
     * Tworzy wyjątek reprezentujący sytuację kiedy lot był anulowany
     * @return stworzony wyjątek
     */
    public static FlightException cancelled() {
        return new FlightException(FLIGHT_CANCELLED);
    }

    /**
     * Tworzy wyjątek reprezentujący brak lotu w bazie.
     * @return stworzony wyjątek
     */
    public static FlightException notFound() {
        return new FlightException(FLIGHT_NOT_FOUND);
    }

    /**
     * Tworzy wątek reprezentujący próbę zmiany daty lotu na wcześniejszą.
     * @return stworzony wyjątek
     */
    public static FlightException cantMakeEarlier() {
        return new FlightException(FLIGHT_MADE_EARLIER);
    }

    /**
     * Tworzy wyjątek reprezentujący niezgodność oczekiwanej ceny z aktualną ceną
     * @return wyjątek reprezentujący niezgodność oczekiwanej ceny z aktualną ceną
     */
    public static FlightException priceChanged() {
        return new FlightException(FLIGHT_PRICE_CHANGED);
    }

    /**
     * Tworzy wyjątek reprezentujący niezgodność z oczekiwaną datą wylotu
     * @return wyjątek reprezentujący niezgodność z oczekiwaną datą wylotu
     */
    public static FlightException departureTimeChanged() {
        return new FlightException(FLIGHT_DEPARTURE_TIME_CHANGED);
    }

}
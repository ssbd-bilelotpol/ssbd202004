package pl.lodz.p.it.ssbd2020.ssbd04.exceptions;

import pl.lodz.p.it.ssbd2020.ssbd04.entities.Airport;

import static pl.lodz.p.it.ssbd2020.ssbd04.common.I18n.*;

/**
 * Wyjątek odpowiadający hierarchii klas Airport.
 */
public class AirportException extends AppBaseException {
    private final Airport Airport;

    private AirportException(String message) {
        super(message);
        this.Airport = null;
    }

    private AirportException(String message, Airport Airport) {
        super(message);
        this.Airport = Airport;
    }

    /**
     * Tworzy wyjątek odpowiadający sytuacji kiedy lotnisko o tym samym kodzie istnieje już w bazie.
     *
     * @param airport lotnisko, które nie mogło zostać stworzone
     * @return stworzony wyjątek
     */
    public static AirportException codeNotUnique(Airport airport) {
        return new AirportException(AIRPORT_CODE_NOT_UNIQUE, airport);
    }

    /**
     * Tworzy wyjątek reprezentujący brak lotniska w bazie.
     *
     * @return stworzony wyjątek
     */
    public static AirportException notFound() {
        return new AirportException(AIRPORT_NOT_FOUND);
    }

    /**
     * Tworzy wyjątek reprezentujący sytuację, w której czynność nie może zostać wykonana ze względu na inny obiekt
     * używający tego lotniska.
     *
     * @return stworzony wyjątek
     */
    public static AirportException inUse() {
        return new AirportException(AIRPORT_IN_USE);
    }
}

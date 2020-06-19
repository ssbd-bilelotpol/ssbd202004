package pl.lodz.p.it.ssbd2020.ssbd04.exceptions;

import pl.lodz.p.it.ssbd2020.ssbd04.entities.SeatClass;

import static pl.lodz.p.it.ssbd2020.ssbd04.common.I18n.*;

/**
 * Reprezentuje błąd występujący podczas przetwarzania
 * logiki biznesowej klas związanych z klasami siedzeń.
 */
public class SeatClassException extends AppBaseException {
    private final SeatClass seatClass;

    private SeatClassException(String message) {
        super(message);
        this.seatClass = null;
    }

    private SeatClassException(String message, Throwable cause, SeatClass seatClass) {
        super(message, cause);
        this.seatClass = seatClass;
    }

    private SeatClassException(String message, SeatClass seatClass) {
        super(message);
        this.seatClass = seatClass;
    }

    private SeatClassException(String message, Throwable cause) {
        super(message, cause);
        this.seatClass = null;
    }

    /**
     * Tworzy wyjątek reprezentujący brak danej klasy siedzeń.
     *
     * @return wyjątek reprezentujący brak danej klasy siedzeń.
     */
    public static SeatClassException notFound(Throwable cause) {
        return new SeatClassException(SEAT_CLASS_NOT_FOUND, cause);
    }

    /**
     * Tworzy wyjątek reprezentujący wykorzystanie klasy siedzeń przez inne encje.
     *
     * @param seatClass klasa siedzeń, która jest wykorzystywana.
     * @return wyjątek reprezentujący wykorzystanie klasy siedzeń przez inne encje.
     */
    public static SeatClassException inUse(SeatClass seatClass) {
        return new SeatClassException(SEAT_CLASS_IN_USE, seatClass);
    }

    /**
     * Tworzy wyjątek reprezentujący brak możliwości utworzenia klasy siedzeń
     * ze względu na nieunikatową nazwę.
     *
     * @param seatClass klasa siedzeń.
     * @return wyjątek reprezentujący nieunikatową nazwę klasy siedzeń.
     */
    public static SeatClassException nameTaken(SeatClass seatClass) {
        return new SeatClassException(SEAT_CLASS_NAME_TAKEN, seatClass);
    }

    /**
     * Tworzy wyjątek reprezentujący brak możliwości utworzenia klasy siedzeń
     * z dodatkami, ponieważ już istnieją dodatki o takiej nazwie.
     *
     * @param seatClass klasa siedzeń.
     * @return wyjątek reprezentujący brak możliwości utworzenia dodatku.
     */
    public static SeatClassException benefitExists(SeatClass seatClass) {
        return new SeatClassException(SEAT_CLASS_BENEFIT_EXISTS, seatClass);
    }

    /**
     * Tworzy wyjątek reprezentujący niezgodność oczekiwanej ceny z aktualną ceną
     *
     * @param seatClass klasa siedzeń.
     * @return wyjątek reprezentujący niezgodność oczekiwanej ceny z aktualną ceną
     */
    public static SeatClassException priceChanged(SeatClass seatClass) {
        return new SeatClassException(SEAT_CLASS_PRICE_CHANGED, seatClass);
    }

}

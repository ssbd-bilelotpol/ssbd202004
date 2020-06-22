package pl.lodz.p.it.ssbd2020.ssbd04.exceptions;

import pl.lodz.p.it.ssbd2020.ssbd04.entities.SeatClass;

import static pl.lodz.p.it.ssbd2020.ssbd04.common.I18n.SEAT_NOT_FOUND;

/**
 * Wyjątek odpowiadający hierarchii klas Passenger.
 */
public class SeatException extends AppBaseException {
    private final SeatClass seatClass;

    private SeatException(String message) {
        super(message);
        this.seatClass = null;
    }

    private SeatException(String message, Throwable cause, SeatClass seatClass) {
        super(message, cause);
        this.seatClass = seatClass;
    }

    private SeatException(String message, SeatClass seatClass) {
        super(message);
        this.seatClass = seatClass;
    }

    private SeatException(String message, Throwable cause) {
        super(message, cause);
        this.seatClass = null;
    }

    /**
     * Tworzy wyjątek reprezentujący brak danego siedzenia.
     * @param cause przyczyna problemu
     * @return wyjątek reprezentujący brak danego siedzenia.
     */
    public static SeatException notFound(Throwable cause) {
        return new SeatException(SEAT_NOT_FOUND, cause);
    }

    /**
     * Tworzy wyjątek reprezentujący brak danego siedzenia.
     *
     * @return wyjątek reprezentujący brak danego siedzenia.
     */
    public static SeatException notFound() {
        return new SeatException(SEAT_NOT_FOUND);
    }
}

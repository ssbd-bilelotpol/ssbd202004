package pl.lodz.p.it.ssbd2020.ssbd04.exceptions;


import pl.lodz.p.it.ssbd2020.ssbd04.entities.Connection;

import static pl.lodz.p.it.ssbd2020.ssbd04.common.I18n.*;

/**
 * Wyjątek odpowiadający hierarchii klas Connection.
 */
public class ConnectionException extends AppBaseException {
    private final Connection connection;

    private ConnectionException(String message) {
        super(message);
        this.connection = null;
    }

    private ConnectionException(String message, Throwable cause, Connection Connection) {
        super(message, cause);
        this.connection = Connection;
    }

    private ConnectionException(String message, Connection Connection) {
        super(message);
        this.connection = Connection;
    }

    private ConnectionException(String message, Throwable cause) {
        super(message, cause);
        this.connection = null;
    }

    /**
     * Tworzy wyjątek reprezentujący sytuację kiedy tworzone połączenie już istnieje.
     * @return stworzony wyjątek
     */
    public static ConnectionException exists() {
        return new ConnectionException(CONNECTION_EXISTS);
    }

    /**
     * Tworzy wyjątek reprezentujący sytuację, w której lotnisko docelowe nie istnieje.
     * @return stworzony wyjątek
     */
    public static ConnectionException destinationAirportNotFound() {
        return new ConnectionException(CONNECTION_TARGET_NOT_FOUND);
    }

    /**
     * Tworzy wyjątek reprezentujący sytuację, w której lotnisko startowe nie istnieje.
     * @return stworzony wyjątek
     */
    public static ConnectionException sourceAirportNotFound() {
        return new ConnectionException(CONNECTION_SOURCE_NOT_FOUND);
    }

    /**
     * Tworzy wyjątek reprezentujacy sytuację, w której czynność nie może zostać wykonana ze względu na inny obiekt
     * używajacy tego połączenia.
     * @param connection połączenie, na którym nie udało się wykonać czynności
     * @return stworzony wyjątek
     */
    public static ConnectionException inUse(Connection connection) {
        return new ConnectionException(CONNECTION_IN_USE, connection);
    }

    /**
     * Tworzy wyjątek reprezentujący sytuację, w której relacja nie istnieje.
     * @return stworzony wyjątek
     */
    public static ConnectionException notFound() {
        return new ConnectionException(CONNECTION_NOT_FOUND);
    }
}

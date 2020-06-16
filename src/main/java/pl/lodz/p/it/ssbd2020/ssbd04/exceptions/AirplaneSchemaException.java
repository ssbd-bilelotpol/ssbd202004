package pl.lodz.p.it.ssbd2020.ssbd04.exceptions;

import pl.lodz.p.it.ssbd2020.ssbd04.entities.AirplaneSchema;

import static pl.lodz.p.it.ssbd2020.ssbd04.common.I18n.*;

/**
 * Reprezentuje błąd występujący podczas przetwarzania
 * logiki biznesowej klas związanych z schematami samolotów.
 */
public class AirplaneSchemaException extends AppBaseException {
    private final AirplaneSchema airplaneSchema;

    private AirplaneSchemaException(String message) {
        super(message);
        this.airplaneSchema = null;
    }

    private AirplaneSchemaException(String message, Throwable cause, AirplaneSchema airplaneSchema) {
        super(message, cause);
        this.airplaneSchema = airplaneSchema;
    }

    private AirplaneSchemaException(String message, AirplaneSchema airplaneSchema) {
        super(message);
        this.airplaneSchema = airplaneSchema;
    }

    private AirplaneSchemaException(String message, Throwable cause) {
        super(message, cause);
        this.airplaneSchema = null;
    }

    public static AirplaneSchemaException invalidData() {
        return new AirplaneSchemaException(AIRPLANE_SCHEMA_INVALID_DATA);
    }


    /**
     * Tworzy wyjątek reprezentujący brak danego schematu samolotu.
     *
     * @return wyjątek reprezentujący brak danego schematu samolotu.
     */
    public static AirplaneSchemaException notFound() {
        return new AirplaneSchemaException(AIRPLANE_SCHEMA_NOT_FOUND);
    }

    /**
     * Tworzy wyjątek reprezentujący brak możliwości wykonania operacji
     * na dabym schemacie samolotu, ponieważ jest on używany przez inne encje.
     *
     * @param airplaneSchema schemat samolotu
     * @return wyjątek reprezentujący wykorzystanie schematu samolotu przez inne encje.
     */
    public static AirplaneSchemaException inUse(AirplaneSchema airplaneSchema) {
        return new AirplaneSchemaException(AIRPLANE_SCHEMA_IN_USE, airplaneSchema);
    }
}

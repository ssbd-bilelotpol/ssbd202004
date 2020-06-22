package pl.lodz.p.it.ssbd2020.ssbd04.controllers.converters;

import pl.lodz.p.it.ssbd2020.ssbd04.common.Utils;

import javax.ws.rs.ext.ParamConverter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Klasa konwertująca do LocalDateTime w formacie yyyy-MM-dd'T'HH:mm:ss.SSSXXX.
 */
public class LocalDateConverter implements ParamConverter<LocalDateTime> {
    private static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSXXX");

    @Override
    public LocalDateTime fromString(String value) {
        if (Utils.isNullOrEmpty(value))
            return null;
        return LocalDateTime.parse(value, formatter);
    }

    @Override
    public String toString(LocalDateTime value) {
        return value.format(formatter);
    }
}

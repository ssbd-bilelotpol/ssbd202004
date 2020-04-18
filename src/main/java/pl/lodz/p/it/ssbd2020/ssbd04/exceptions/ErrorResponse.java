package pl.lodz.p.it.ssbd2020.ssbd04.exceptions;

import java.util.Map;
import java.util.Set;

/**
 * Reprezentuje komunikat błędu przesyłany do użytkownika.
 */
public class ErrorResponse {
    private final String message;
    private Map<String, Set<String>> errors;

    public ErrorResponse(String message) {
        this.message = message;
    }

    public ErrorResponse(String message, Map<String, Set<String>> errors) {
        this(message);
        this.errors = errors;
    }

    public String getMessage() {
        return message;
    }

    public Map<String, Set<String>> getErrors() {
        return errors;
    }
}
package pl.lodz.p.it.ssbd2020.ssbd04.exceptions;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.Path;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;
import java.util.*;
import java.util.stream.Collectors;

import static javax.ws.rs.core.Response.Status.BAD_REQUEST;
import static pl.lodz.p.it.ssbd2020.ssbd04.utils.I18n.REST_VALIDATION_ERROR;

/**
 * Zajmuje się przetwarzaniem wyjątków pochodzących z BeanValidation na odpowiednie odpowiedzi HTTP.
 */
@Provider
public class ConstraintValidationExceptionMapper implements ExceptionMapper<ConstraintViolationException> {

    @Override
    public Response toResponse(ConstraintViolationException exception) {
        Map<String, Set<String>> errors = new HashMap<>();
        for (ConstraintViolation v : exception.getConstraintViolations()) {
            String key = getLast(v.getPropertyPath().iterator());
            Set<String> current = errors.getOrDefault(key, new HashSet<>());
            current.add(v.getMessage());
            errors.put(key, current);
        }

        return Response.status(BAD_REQUEST)
                .entity(new ErrorResponse(REST_VALIDATION_ERROR, errors)).build();
    }

    private String getLast(Iterator<Path.Node> iterator) {
        String name = null;
        while (iterator.hasNext()) {
            name = iterator.next().getName();
        }

        return name;
    }
}

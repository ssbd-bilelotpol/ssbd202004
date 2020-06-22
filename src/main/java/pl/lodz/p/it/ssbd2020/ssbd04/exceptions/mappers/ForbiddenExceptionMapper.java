package pl.lodz.p.it.ssbd2020.ssbd04.exceptions.mappers;

import pl.lodz.p.it.ssbd2020.ssbd04.exceptions.ErrorResponse;
import pl.lodz.p.it.ssbd2020.ssbd04.exceptions.ForbiddenException;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import static javax.ws.rs.core.Response.Status.FORBIDDEN;

/**
 * Zajmuje się przetwarzaniem wyjątków wygenerowanych przez logikę biznesową na odpowiednie odpowiedzi HTTP.
 */
@Provider
public class ForbiddenExceptionMapper implements ExceptionMapper<ForbiddenException> {

    /**
     * Odwzorowuje wyjątek ForbiddenException na kod 401 HTTP
     *
     * @param exception wyjątek typu ForbiddenException
     * @return odpowiedź HTTP
     */
    @Override
    public Response toResponse(ForbiddenException exception) {
        return Response.status(FORBIDDEN)
                .entity(new ErrorResponse(exception.getMessage())).build();
    }
}

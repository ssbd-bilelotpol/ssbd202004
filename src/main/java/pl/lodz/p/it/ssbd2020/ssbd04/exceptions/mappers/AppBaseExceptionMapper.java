package pl.lodz.p.it.ssbd2020.ssbd04.exceptions.mappers;

import pl.lodz.p.it.ssbd2020.ssbd04.exceptions.AppBaseException;
import pl.lodz.p.it.ssbd2020.ssbd04.exceptions.ErrorResponse;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import static javax.ws.rs.core.Response.Status.BAD_REQUEST;

/**
 * Zajmuje się przetwarzaniem wyjątków wygenerowanych przez logikę biznesową na odpowiednie odpowiedzi HTTP.
 */
@Provider
public class AppBaseExceptionMapper implements ExceptionMapper<AppBaseException> {
    @Override
    public Response toResponse(AppBaseException exception) {
        return Response.status(BAD_REQUEST)
                .entity(new ErrorResponse(exception.getMessage())).build();
    }
}

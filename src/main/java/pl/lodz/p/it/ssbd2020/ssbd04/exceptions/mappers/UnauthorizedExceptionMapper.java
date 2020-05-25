package pl.lodz.p.it.ssbd2020.ssbd04.exceptions.mappers;

import pl.lodz.p.it.ssbd2020.ssbd04.exceptions.ErrorResponse;
import pl.lodz.p.it.ssbd2020.ssbd04.exceptions.UnauthorizedException;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import static javax.ws.rs.core.Response.Status.UNAUTHORIZED;

/**
 * Zajmuje się przetwarzaniem wyjątków wygenerowanych przez logikę biznesową na odpowiednie odpowiedzi HTTP.
 */
@Provider
public class UnauthorizedExceptionMapper implements ExceptionMapper<UnauthorizedException> {
    @Override
    public Response toResponse(UnauthorizedException exception) {
        return Response.status(UNAUTHORIZED)
                .entity(new ErrorResponse(exception.getMessage())).build();
    }
}

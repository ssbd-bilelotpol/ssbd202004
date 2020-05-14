package pl.lodz.p.it.ssbd2020.ssbd04.exceptions;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import static pl.lodz.p.it.ssbd2020.ssbd04.common.I18n.PROCESSING_ERROR;

@Provider
public class AppExceptionMapper implements ExceptionMapper<Exception> {
    @Override
    public Response toResponse(Exception exception) {
        return Response.status(Response.Status.BAD_REQUEST)
                .entity(new ErrorResponse(PROCESSING_ERROR))
                .build();
    }
}

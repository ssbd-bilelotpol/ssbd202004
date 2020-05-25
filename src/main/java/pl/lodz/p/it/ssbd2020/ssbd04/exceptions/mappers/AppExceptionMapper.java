package pl.lodz.p.it.ssbd2020.ssbd04.exceptions.mappers;

import pl.lodz.p.it.ssbd2020.ssbd04.exceptions.ErrorResponse;

import javax.ejb.EJBAccessException;
import javax.ejb.EJBException;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import static pl.lodz.p.it.ssbd2020.ssbd04.common.I18n.PROCESSING_ERROR;
import static pl.lodz.p.it.ssbd2020.ssbd04.common.I18n.RESOURCE_NOT_FOUND;

@Provider
public class AppExceptionMapper implements ExceptionMapper<EJBException> {
    @Override
    public Response toResponse(EJBException exception) {
        if(exception instanceof EJBAccessException) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity(new ErrorResponse(RESOURCE_NOT_FOUND))
                    .build();
        }
        return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                .entity(new ErrorResponse(PROCESSING_ERROR))
                .build();
    }
}

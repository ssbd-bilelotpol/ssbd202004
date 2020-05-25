package pl.lodz.p.it.ssbd2020.ssbd04.exceptions.mappers;

import pl.lodz.p.it.ssbd2020.ssbd04.exceptions.ErrorResponse;

import javax.ejb.EJBException;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import static pl.lodz.p.it.ssbd2020.ssbd04.common.I18n.PROCESSING_ERROR;

@Provider
public class AppExceptionMapper implements ExceptionMapper<EJBException> {
    @Override
    public Response toResponse(EJBException exception) {
        return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                .entity(new ErrorResponse(PROCESSING_ERROR))
                .build();
    }
}

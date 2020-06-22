package pl.lodz.p.it.ssbd2020.ssbd04.exceptions.mappers;

import pl.lodz.p.it.ssbd2020.ssbd04.exceptions.ErrorResponse;

import javax.ejb.EJBAccessException;
import javax.ejb.EJBException;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import static pl.lodz.p.it.ssbd2020.ssbd04.common.I18n.PROCESSING_ERROR;
import static pl.lodz.p.it.ssbd2020.ssbd04.common.I18n.RESOURCE_NOT_FOUND;

/**
 * Odwzorowuje wyjątek biznesowy na odpowiedź z odpowiednim kod HTTP i kodem błędu.
 * Odpowiedź jest propagowana do warstwy użytkownika.
 */
@Provider
public class AppExceptionMapper implements ExceptionMapper<EJBException> {

    /**
     * Jeśli wyjątek jest typu EJBAccessException, to zwracamy kod HTTP 404 oraz odpowiedni kod błędu.
     * W przeciwnym przypadku kod HTTP 500 oraz odpowiedni kod błędu.
     *
     * @param exception wyjątek hierarchi EJBException
     * @return odpowiedź HTTP
     */
    @Override
    public Response toResponse(EJBException exception) {
        if (exception instanceof EJBAccessException) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity(new ErrorResponse(RESOURCE_NOT_FOUND))
                    .build();
        }
        return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                .entity(new ErrorResponse(PROCESSING_ERROR))
                .build();
    }
}

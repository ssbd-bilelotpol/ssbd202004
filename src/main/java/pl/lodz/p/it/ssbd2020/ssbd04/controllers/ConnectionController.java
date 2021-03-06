package pl.lodz.p.it.ssbd2020.ssbd04.controllers;

import pl.lodz.p.it.ssbd2020.ssbd04.common.Utils;
import pl.lodz.p.it.ssbd2020.ssbd04.exceptions.AppBaseException;
import pl.lodz.p.it.ssbd2020.ssbd04.exceptions.ConnectionException;
import pl.lodz.p.it.ssbd2020.ssbd04.mol.dto.ConnectionCreateDto;
import pl.lodz.p.it.ssbd2020.ssbd04.mol.dto.ConnectionDto;
import pl.lodz.p.it.ssbd2020.ssbd04.mol.endpoints.ConnectionEndpoint;
import pl.lodz.p.it.ssbd2020.ssbd04.security.EtagBinding;
import pl.lodz.p.it.ssbd2020.ssbd04.security.MessageSigner;

import javax.inject.Inject;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

import static javax.ws.rs.core.Response.Status.NOT_FOUND;

/**
 * Odpowiada zasobom reprezentującym logikę przetwarzania połączeń.
 * Konwertuje DTO na model biznesowy oraz zajmuje się walidacją danych.
 */
@Path("/connections")
public class ConnectionController extends AbstractController {

    @Inject
    private ConnectionEndpoint connectionEndpoint;

    @Inject
    private MessageSigner messageSigner;

    /**
     * Wyszukuje połączenia pomiędzy lotniskami o danych kodach.
     *
     * @param destinationCode kod lotniska przylotu.
     * @param sourceCode      kod lotniska wylotu.
     * @param phrase          szukana nazwa lotniska.
     * @return połączenia spełniające podane kryterium.
     * @throws AppBaseException gdy operacja się nie powiedzie
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<ConnectionDto> find(@QueryParam("destinationCode") String destinationCode,
                                    @QueryParam("sourceCode") String sourceCode,
                                    @QueryParam("phrase") String phrase) throws AppBaseException {
        if (Utils.isNullOrEmpty(sourceCode) && Utils.isNullOrEmpty(destinationCode)) {
            return repeat(connectionEndpoint, () -> connectionEndpoint.findByPhrase(phrase));
        }
        return repeat(connectionEndpoint, () -> connectionEndpoint.find(destinationCode, sourceCode));
    }

    /**
     * Zwraca połączenie o podanym identyfikatorze.
     *
     * @param id identyfikator połączenia.
     * @return połączenie o podanym identyfikatorze.
     * @throws AppBaseException gdy operacja się nie powiedzie
     */
    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response findById(@PathParam("id") Long id) throws AppBaseException {
        ConnectionDto connectionDto;
        try {
            connectionDto = repeat(connectionEndpoint, () -> connectionEndpoint.findById(id));
        } catch (ConnectionException e) {
            return Response
                    .status(NOT_FOUND)
                    .build();
        }
        return Response.ok()
                .entity(connectionDto)
                .tag(messageSigner.sign(connectionDto))
                .build();
    }

    /**
     * Tworzy i zapisuje w bazie połączenie.
     *
     * @param connectionCreateDto dane nowego połączenia.
     * @return stworzone połączenie.
     * @throws AppBaseException gdy operacja się nie powiedzie
     */
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public ConnectionDto create(@NotNull @Valid ConnectionCreateDto connectionCreateDto) throws AppBaseException {
        return repeat(connectionEndpoint, () -> connectionEndpoint.create(connectionCreateDto));
    }

    /**
     * Usuwa połączenie o podanym identyfikatorze.
     *
     * @param id identyfikator połączenia do usunięcia.
     * @throws AppBaseException gdy operacja się nie powiedzie
     */
    @DELETE
    @Path("/{id}")
    @EtagBinding
    public void delete(@PathParam("id") Long id) throws AppBaseException {
        repeat(connectionEndpoint, () -> connectionEndpoint.delete(id));
    }

    /**
     * Modyfikuje istniejące połączenie.
     *
     * @param id            identyfikator połączenia, które ma zostać zmodyfikowane.
     * @param connectionDto dane, które mają zostać zapisane.
     * @throws AppBaseException gdy operacja się nie powiedzie
     */
    @PUT
    @Path("/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @EtagBinding
    public void update(@PathParam("id") Long id, ConnectionCreateDto connectionDto) throws AppBaseException {
        repeat(connectionEndpoint, () -> connectionEndpoint.update(id, connectionDto));
    }
}

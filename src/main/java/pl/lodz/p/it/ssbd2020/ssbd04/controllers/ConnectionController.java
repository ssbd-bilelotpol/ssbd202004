package pl.lodz.p.it.ssbd2020.ssbd04.controllers;

import pl.lodz.p.it.ssbd2020.ssbd04.exceptions.AppBaseException;
import pl.lodz.p.it.ssbd2020.ssbd04.mol.dto.ConnectionCreateDto;
import pl.lodz.p.it.ssbd2020.ssbd04.mol.dto.ConnectionDto;
import pl.lodz.p.it.ssbd2020.ssbd04.mol.endpoints.ConnectionEndpoint;

import javax.inject.Inject;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;

/**
 * Odpowiada zasobom reprezentującym logikę przetwarzania połączeń.
 * Konwertuje DTO na model biznesowy oraz zajmuje się walidacją danych.
 */
@Path("/connections")
public class ConnectionController extends AbstractController {
    @Inject
    private ConnectionEndpoint connectionEndpoint;

    /**
     * Wyszukuje połączenia pomiędzy lotniskami o danych kodach.
     * @param destinationCode kod lotniska przylotu
     * @param sourceCode kod lotniska wylotu
     * @return połączenia spełniające podane kryterium
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public List<ConnectionDto> find(@QueryParam("destinationCode") String destinationCode, @QueryParam("sourceCode") String sourceCode) throws AppBaseException {
        return repeat(connectionEndpoint, () -> connectionEndpoint.find(destinationCode, sourceCode));
    }

    /**
     * Zwraca połączenie o podanym identyfikatorze.
     * @param id identyfikator połączenia
     * @return połączenie o podanym identyfikatorze
     */
    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public ConnectionDto findById(@PathParam("id") Long id) throws AppBaseException {
        return repeat(connectionEndpoint, () -> connectionEndpoint.findById(id));
    }

    /**
     * Tworzy i zapisuje w bazie połączenie.
     * @param connectionCreateDto dane nowego połączenia
     * @return stworzone połączenie
     */
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public ConnectionDto create(@NotNull @Valid ConnectionCreateDto connectionCreateDto) throws AppBaseException {
        return repeat(connectionEndpoint, () -> connectionEndpoint.create(connectionCreateDto));
    }

    /**
     * Usuwa połączenie o podanym identyfikatorze.
     * @param id identyfikator połączenia do usunięcia
     */
    @DELETE
    @Path("/{id}")
    public void delete(@PathParam("id") Long id) {
        throw new UnsupportedOperationException();
    }

    /**
     * Modyfikuje istniejące połączenie.
     * @param id identyfikator połączenia, które ma zostać zmodyfikowane
     * @param connectionDto dane, które mają zostać zapisane
     */
    @PUT
    @Path("/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    public void update(@PathParam("id") Long id, ConnectionDto connectionDto) {
        throw new UnsupportedOperationException();
    }
}

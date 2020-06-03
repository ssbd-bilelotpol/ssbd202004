package pl.lodz.p.it.ssbd2020.ssbd04.controllers;

import pl.lodz.p.it.ssbd2020.ssbd04.exceptions.AppBaseException;
import pl.lodz.p.it.ssbd2020.ssbd04.mol.dto.ConnectionDto;
import pl.lodz.p.it.ssbd2020.ssbd04.mol.dto.ConnectionQueryDto;
import pl.lodz.p.it.ssbd2020.ssbd04.mol.endpoints.ConnectionEndpoint;
import pl.lodz.p.it.ssbd2020.ssbd04.security.Role;

import javax.annotation.security.RolesAllowed;
import javax.inject.Inject;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

/**
 * Odpowiada zasobom reprezentującym logikę przetwarzania połączeń.
 * Konwertuje DTO na model biznesowy oraz zajmuje się walidacją danych.
 */
@Path("/connections")
public class ConnectionController extends AbstractController {
    @Inject
    private ConnectionEndpoint connectionEndpoint;

    /**
     * Wyszukuje połączenia na podstawie przekazanego kryterium.
     * @param query kryterium
     * @return połączenia spełniające podane kryterium
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public ConnectionDto find(@NotNull @Valid ConnectionQueryDto query) throws AppBaseException {
        return repeat(connectionEndpoint, () -> connectionEndpoint.find(query));
    }

    /**
     * Zwraca połączenie o podanym identyfikatorze.
     * @param id identyfikator połączenia
     * @return połączenie o podanym identyfikatorze
     */
    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public ConnectionDto findById(@PathParam("id") Long id) {
        throw new UnsupportedOperationException();
    }

    /**
     * Tworzy i zapisuje w bazie połączenie.
     * @param connectionDto dane nowego połączenia.
     * @return stworzone połączenie.
     */
    @POST
    @RolesAllowed(Role.CreateConnection)
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public ConnectionDto create(@NotNull @Valid ConnectionDto connectionDto) throws AppBaseException {
        return repeat(connectionEndpoint, () -> connectionEndpoint.create(connectionDto));
    }

    /**
     * Usuwa połączenie o podanym identyfikatorze.
     * @param id identyfikator połączenia do usunięcia.
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

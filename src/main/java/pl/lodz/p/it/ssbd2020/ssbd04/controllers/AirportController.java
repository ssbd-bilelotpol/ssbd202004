package pl.lodz.p.it.ssbd2020.ssbd04.controllers;

import pl.lodz.p.it.ssbd2020.ssbd04.exceptions.AppBaseException;
import pl.lodz.p.it.ssbd2020.ssbd04.mol.dto.AirportDto;
import pl.lodz.p.it.ssbd2020.ssbd04.mol.dto.AirportQueryDto;
import pl.lodz.p.it.ssbd2020.ssbd04.mol.endpoints.AirportEndpoint;

import javax.inject.Inject;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;

/**
 * Odpowiada zasobom reprezentującym logikę przetwarzania lotnisk.
 * Konwertuje DTO na model biznesowy oraz zajmuje się walidacją danych.
 */
@Path("/airports")
public class AirportController extends AbstractController {

    @Inject
    private AirportEndpoint airportEndpoint;

    /**
     * Wyszukuje lotniska na podstawie przekazanego kryterium.
     * @param query kryterium.
     * @return lotniska spełniające podane kryterium.
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public List<AirportDto> find(@NotNull @Valid AirportQueryDto query) {
        throw new UnsupportedOperationException();
    }

    /**
     * Zwraca lotnisko o podanym identyfikatorze.
     * @param id identyfikator lotniska.
     * @return lotnisko o podanym identyfikatorze.
     */
    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public AirportDto findById(@PathParam("id") Long id) {
        throw new UnsupportedOperationException();
    }

    /**
     * Tworzy i zapisuje w bazie lotnisko.
     * @param airportDto dane nowego lotniska.
     * @return stworzone lotnisko.
     */
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public AirportDto create(@NotNull @Valid AirportDto airportDto) throws AppBaseException {
        return repeat(airportEndpoint, () -> airportEndpoint.create(airportDto));
    }

    /**
     * Usuwa lotnisko o podanym identyfikatorze.
     * @param id identyfikator lotniska do usunięcia.
     */
    @DELETE
    @Path("/{id}")
    public void delete(@PathParam("id") Long id) {
        throw new UnsupportedOperationException();
    }

    /**
     * Modyfikuje istniejące lotnisko.
     * @param id identyfikator lotniska, które ma zostać zmodyfikowane.
     * @param airportDto dane, które mają zostać zapisane.
     */
    @PUT
    @Path("/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    public void update(@PathParam("id") Long id, AirportDto airportDto) {
        throw new UnsupportedOperationException();
    }
}

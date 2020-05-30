package pl.lodz.p.it.ssbd2020.ssbd04.controllers;

import pl.lodz.p.it.ssbd2020.ssbd04.exceptions.AppBaseException;
import pl.lodz.p.it.ssbd2020.ssbd04.mol.dto.AirportDto;
import pl.lodz.p.it.ssbd2020.ssbd04.mol.endpoints.AirportEndpoint;

import javax.inject.Inject;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
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
     * @param city miasto.
     * @param code kod lotniska.
     * @param country kraj.
     * @param name nazwa lotniska.
     * @return lotniska spełniające podane kryterium.
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public List<AirportDto> find(@QueryParam("name") String name, @QueryParam("code") String code, @QueryParam("country") String country, @QueryParam("city") String city) throws AppBaseException {
        return repeat(airportEndpoint, () -> airportEndpoint.find(name, code, country, city));
    }

    /**
     * Zwraca lotnisko o podanym identyfikatorze.
     * @param id identyfikator lotniska.
     * @return lotnisko o podanym identyfikatorze.
     */
    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response findById(@PathParam("id") String id) throws AppBaseException {
        try {
            Long airportId = Long.parseLong(id);
            AirportDto dto = repeat(airportEndpoint, () -> airportEndpoint.findById(airportId));
            return Response.ok()
                    .entity(dto)
                    .build();
        } catch (NumberFormatException e) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .build();
        }
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

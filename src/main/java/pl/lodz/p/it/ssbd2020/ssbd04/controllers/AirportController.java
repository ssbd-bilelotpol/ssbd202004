package pl.lodz.p.it.ssbd2020.ssbd04.controllers;

import pl.lodz.p.it.ssbd2020.ssbd04.exceptions.AppBaseException;
import pl.lodz.p.it.ssbd2020.ssbd04.mol.dto.AirportDto;
import pl.lodz.p.it.ssbd2020.ssbd04.mol.endpoints.AirportEndpoint;
import pl.lodz.p.it.ssbd2020.ssbd04.security.MessageSigner;
import pl.lodz.p.it.ssbd2020.ssbd04.validation.AirportCity;
import pl.lodz.p.it.ssbd2020.ssbd04.validation.AirportCode;
import pl.lodz.p.it.ssbd2020.ssbd04.validation.AirportCountry;
import pl.lodz.p.it.ssbd2020.ssbd04.validation.AirportName;

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

    @Inject
    private MessageSigner messageSigner;

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
    public List<AirportDto> find(@AirportName @Valid @QueryParam("name") String name, @AirportCode @Valid @QueryParam("code") String code,
                                 @AirportCountry @Valid @QueryParam("country") String country, @AirportCity @Valid @QueryParam("city") String city) throws AppBaseException {
        return repeat(airportEndpoint, () -> airportEndpoint.find(name, code, country, city));
    }

    /**
     * Zwraca lotnisko o podanym identyfikatorze.
     * @param code kod lotniska.
     * @return lotnisko o podanym identyfikatorze.
     */
    @GET
    @Path("/{code}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response findByCode(@AirportCode @Valid @PathParam("code") String code) throws AppBaseException {
        AirportDto airportDto = repeat(airportEndpoint, () -> airportEndpoint.findByCode(code));
        return Response
                .ok()
                .entity(airportDto)
                .tag(messageSigner.sign(airportDto))
                .build();

    }

    /**
     * Zwraca nazwy nazwy krajów dostępnych lotnisk.
     * @return nazwy krajów dostępnych lotnisk.
     * @throws AppBaseException
     */
    @GET
    @Path("/countries")
    @Produces(MediaType.APPLICATION_JSON)
    public List<String> getCountries() throws AppBaseException {
        return repeat(airportEndpoint, () -> airportEndpoint.getCountries());
    }

    /**
     * Zwraca nazwy miast dostępnych lotnisk.
     * @return nazwy miast dostępnych lotnisk
     * @throws AppBaseException
     */
    @GET
    @Path("/cities")
    @Produces(MediaType.APPLICATION_JSON)
    public List<String> getCities() throws AppBaseException {
        return repeat(airportEndpoint, () -> airportEndpoint.getCities());
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
    public void update(@PathParam("id") @AirportCode String code, AirportDto airportDto) throws AppBaseException {
        repeat(airportEndpoint, () -> airportEndpoint.update(code, airportDto));
    }
}

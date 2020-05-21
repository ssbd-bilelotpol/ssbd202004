package pl.lodz.p.it.ssbd2020.ssbd04.controllers;

import pl.lodz.p.it.ssbd2020.ssbd04.exceptions.AppBaseException;
import pl.lodz.p.it.ssbd2020.ssbd04.mol.dto.AirplaneSchemaDto;
import pl.lodz.p.it.ssbd2020.ssbd04.mol.endpoints.AirplaneSchemaEndpoint;

import javax.inject.Inject;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;

/**
 * Odpowiada zasobom reprezentującym logikę przetwarzania schematów samolotów.
 * Konwertuje DTO na model biznesowy oraz zajmuje się walidacją danych.
 */
@Path("/airplane-schemas")
public class AirplaneSchemaController extends AbstractController {
    @Inject
    private AirplaneSchemaEndpoint airplaneSchemaEndpoint;

    /**
     * Tworzy nowy schemat samolotu z ustalonymi miejscami.
     *
     * @param airplaneSchemaDto dane schematu samolotu.
     * @return stworzony schemat samolotu.
     * @throws AppBaseException gdy operacja tworzenia nie powiedzie się.
     */
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public AirplaneSchemaDto create(@NotNull @Valid AirplaneSchemaDto airplaneSchemaDto) throws AppBaseException {
        throw new UnsupportedOperationException();
    }

    /**
     * Pobiera wszystkie istniejące schematy samolotów.
     *
     * @return listę schematów samolotów.
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<AirplaneSchemaDto> getAll() {
        throw new UnsupportedOperationException();
    }


    /**
     * Znajduje schemat samolotu na podstawie identyfikatora.
     *
     * @param id identyfikator schematu samolotu.
     * @return znaleziony schemat samolotu.
     * @throws AppBaseException gdy operacja nie powiedzie się, bądź schemat nie został znaleziony.
     */
    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public AirplaneSchemaDto findById(@PathParam("id") Long id) {
        throw new UnsupportedOperationException();
    }

    /**
     * Aktualizuje schemat samolotu.
     *
     * @param id  identyfikator schematu samolotu
     * @param dto dane schematu samolotu.
     * @return zaaktualizowany schemat samolotu.
     * @throws AppBaseException gdy schemat jest już używany, zajdzie problem konkurencyjnej modyfikacji, bądź schemat nie istnieje.
     */
    @PUT
    @Path("/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    public void update(@PathParam("id") Long id, AirplaneSchemaDto dto) {
        throw new UnsupportedOperationException();
    }

    /**
     * Usuwa schemat samolotu.
     *
     * @param id identyfikator schematu samolotu.
     * @throws AppBaseException gdy schemat jest używany przez pewien lot, lub gdy schemat nie istnieje.
     */
    @DELETE
    @Path("/{id}")
    public void delete(@PathParam("id") Long id) {
        throw new UnsupportedOperationException();
    }
}

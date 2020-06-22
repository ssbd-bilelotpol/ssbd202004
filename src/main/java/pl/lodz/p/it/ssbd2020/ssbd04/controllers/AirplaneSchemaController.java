package pl.lodz.p.it.ssbd2020.ssbd04.controllers;

import pl.lodz.p.it.ssbd2020.ssbd04.exceptions.AppBaseException;
import pl.lodz.p.it.ssbd2020.ssbd04.mol.dto.AirplaneSchemaDto;
import pl.lodz.p.it.ssbd2020.ssbd04.mol.dto.AirplaneSchemaListDto;
import pl.lodz.p.it.ssbd2020.ssbd04.mol.endpoints.AirplaneSchemaEndpoint;
import pl.lodz.p.it.ssbd2020.ssbd04.security.EtagBinding;
import pl.lodz.p.it.ssbd2020.ssbd04.security.MessageSigner;

import javax.inject.Inject;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

/**
 * Odpowiada zasobom reprezentującym logikę przetwarzania schematów samolotów.
 * Konwertuje DTO na model biznesowy oraz zajmuje się walidacją danych.
 */
@Path("/airplane-schemas")
public class AirplaneSchemaController extends AbstractController {
    @Inject
    private AirplaneSchemaEndpoint airplaneSchemaEndpoint;

    @Inject
    private MessageSigner messageSigner;

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
        return repeat(airplaneSchemaEndpoint, () -> airplaneSchemaEndpoint.create(airplaneSchemaDto));
    }

    /**
     * Znajduje schematy samolotu na podstawie nazwy
     *
     * @param name nazwa schematu samolotu
     * @return znalezione schematu samolotu
     * @throws AppBaseException gdy operacja nie powiedzie się, bądź schemat nie został znaleziony.
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<AirplaneSchemaListDto> findByName(@QueryParam("name") String name) throws AppBaseException {
        return repeat(airplaneSchemaEndpoint, () -> airplaneSchemaEndpoint.findByName(name));
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
    public Response findById(@PathParam("id") Long id) throws AppBaseException {
        AirplaneSchemaDto airplaneSchemaDto = repeat(airplaneSchemaEndpoint, () -> airplaneSchemaEndpoint.findById(id));
        return Response
                .ok()
                .entity(airplaneSchemaDto)
                .tag(messageSigner.sign(airplaneSchemaDto))
                .build();

    }

    /**
     * Aktualizuje schemat samolotu.
     *
     * @param id  identyfikator schematu samolotu
     * @param dto dane schematu samolotu.
     * @throws AppBaseException gdy schemat jest już używany, zajdzie problem konkurencyjnej modyfikacji, bądź schemat nie istnieje.
     */
    @PUT
    @Path("/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @EtagBinding
    public void update(@PathParam("id") Long id, @NotNull @Valid AirplaneSchemaDto dto) throws AppBaseException {
        repeat(airplaneSchemaEndpoint, () -> airplaneSchemaEndpoint.update(id, dto));
    }

    /**
     * Usuwa schemat samolotu.
     *
     * @param id identyfikator schematu samolotu.
     * @throws AppBaseException gdy schemat jest używany przez pewien lot, lub gdy schemat nie istnieje.
     */
    @DELETE
    @Path("/{id}")
    public void delete(@PathParam("id") Long id) throws AppBaseException {
        repeat(airplaneSchemaEndpoint, () -> airplaneSchemaEndpoint.delete(id));
    }
}

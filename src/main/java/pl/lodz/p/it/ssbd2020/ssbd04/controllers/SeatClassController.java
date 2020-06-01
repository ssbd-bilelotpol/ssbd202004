package pl.lodz.p.it.ssbd2020.ssbd04.controllers;

import pl.lodz.p.it.ssbd2020.ssbd04.exceptions.AppBaseException;
import pl.lodz.p.it.ssbd2020.ssbd04.mol.dto.BenefitDto;
import pl.lodz.p.it.ssbd2020.ssbd04.mol.dto.SeatClassDto;
import pl.lodz.p.it.ssbd2020.ssbd04.mol.endpoints.SeatClassEndpoint;

import javax.inject.Inject;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;

/**
 * Odpowiada zasobom reprezentującym logikę przetwarzania klas siedzeń.
 * Konwertuje DTO na model biznesowy oraz zajmuje się walidacją danych.
 */
@Path("/seat-classes")
public class SeatClassController extends AbstractController {

    @Inject
    private SeatClassEndpoint seatClassEndpoint;

    /**
     * Zwraca wszystkie dostępne klasy miejsc, które mogą zostać przypisane do siedzeń.
     *
     * @return listę wszystkich klas miejsc.
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<SeatClassDto> getAll() throws AppBaseException {
        return repeat(seatClassEndpoint, () -> seatClassEndpoint.getAll());
    }

    /**
     * Zwraca wszystkie dostępne dodatki, które mogą zostać przypisane do klas miejsc.
     *
     * @return listę wszystkich dodatków.
     */
    @GET
    @Path("/benefits")
    @Produces(MediaType.APPLICATION_JSON)
    public List<BenefitDto> getAllBenefits() {
        throw new UnsupportedOperationException();
    }

    /**
     * Znajduje klasę miejsc na podstawie jej nazwy.
     *
     * @param name nazwa klasy miejsc.
     * @return dane klasy miejsc.
     * @throws AppBaseException gdy operacja nie powiedzie się, bądź klasa miejsc nie istnieje.
     */
    @GET
    @Path("/{name}")
    @Produces(MediaType.APPLICATION_JSON)
    public SeatClassDto findByName(@NotNull @PathParam("name") String name) throws AppBaseException {
        return repeat(seatClassEndpoint, () -> seatClassEndpoint.findByName(name));
    }

    /**
     * Tworzy nową klasę miejsc.
     *
     * @param seatClassDto dane klasy miejsc.
     * @return utworzoną klasę miejsc.
     * @throws AppBaseException gdy nazwa klasy miejsc jest już zajęta, bądź operacja nie powiodła się.
     */
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public SeatClassDto create(@NotNull @Valid SeatClassDto seatClassDto) throws AppBaseException {
        throw new UnsupportedOperationException();
    }

    /**
     * Usuwa klasę miejsc.
     *
     * @param name nazwa klasy miejsc.
     * @throws AppBaseException gdy klasa miejsc nie istnieje, jest używana przez siedzenie, bądź operacja nie powiodła się.
     */
    @DELETE
    @Path("/{name}")
    public void delete(@NotNull @PathParam("name") String name) throws AppBaseException {
        throw new UnsupportedOperationException();
    }

    /**
     * Aktualizuję klasę miejsc.
     *
     * @param seatClassDto dane klasy miejsc.
     * @throws AppBaseException gdy wystąpił problem konkurencyjnej modyfikacji, klasa miejsc nie istnieje, bądź operacja nie powiodła się.
     */
    @PUT
    @Path("/{name}")
    @Consumes(MediaType.APPLICATION_JSON)
    public void update(@NotNull @PathParam("name") String name, @NotNull @Valid SeatClassDto seatClassDto) throws AppBaseException {
        throw new UnsupportedOperationException();
    }
}

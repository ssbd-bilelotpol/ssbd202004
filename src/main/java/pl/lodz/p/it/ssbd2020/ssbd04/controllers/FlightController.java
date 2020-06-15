package pl.lodz.p.it.ssbd2020.ssbd04.controllers;

import pl.lodz.p.it.ssbd2020.ssbd04.exceptions.AppBaseException;
import pl.lodz.p.it.ssbd2020.ssbd04.mob.dto.TicketDto;
import pl.lodz.p.it.ssbd2020.ssbd04.mol.dto.FlightCreateDto;
import pl.lodz.p.it.ssbd2020.ssbd04.mol.dto.FlightDto;
import pl.lodz.p.it.ssbd2020.ssbd04.mol.dto.SeatDto;
import pl.lodz.p.it.ssbd2020.ssbd04.mol.endpoints.FlightEndpoint;
import pl.lodz.p.it.ssbd2020.ssbd04.validation.FlightCode;

import javax.inject.Inject;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.time.LocalDateTime;
import java.util.List;

/**
 * Odpowiada zasobom reprezentującym logikę przetwarzania lotów.
 * Konwertuje DTO na model biznesowy oraz zajmuje się walidacją danych.
 */
@Path("/flights")
public class FlightController extends AbstractController {
    @Inject
    private FlightEndpoint flightEndpoint;

    /**
     * Wyszukuje loty na podstawie przekazanego kryterium.
     * @param code kod lotu
     * @param connectionId id połączenia
     * @param airplaneId id lotniska
     * @param from data, po której wylatuje lot
     * @param to dat, przed którą wylatuje lot
     * @return loty spełniające podane kryterium
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<FlightDto> find(@QueryParam("code") String code, @QueryParam("connection") Long connectionId,
                                @QueryParam("airplane") Long airplaneId, @QueryParam("from") LocalDateTime from,
                                @QueryParam("to") LocalDateTime to) throws AppBaseException {
        return repeat(flightEndpoint, () -> flightEndpoint.find(code, connectionId, airplaneId, from, to));
    }

    /**
     * Zwraca loty o podanym identyfikatorze.
     * @param code identyfikator lotu
     * @return lot o podanym identyfikatorze
     */
    @GET
    @Path("/{code}")
    @Produces(MediaType.APPLICATION_JSON)
    public FlightDto findByCode(@PathParam("code") @Valid @FlightCode String code) throws AppBaseException {
        return repeat(flightEndpoint, () -> flightEndpoint.findByCode(code));
    }

    @GET
    @Path("/{id}/taken-seats")
    @Produces(MediaType.APPLICATION_JSON)
    public List<SeatDto> getTakenSeats(@PathParam("id") Long id) {
        throw new UnsupportedOperationException();
    }

    /**
     * Tworzy i zapisuje w bazie lot.
     * @param flightDto dane nowego lotu.
     * @return stworzony lot.
     */
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public FlightDto create(@NotNull @Valid FlightCreateDto flightDto) throws AppBaseException {
        return repeat(flightEndpoint, () -> flightEndpoint.create(flightDto));
    }

    /**
     * Usuwa lot o podanym identyfikatorze.
     * @param id identyfikator lotu do usunięcia.
     */
    @DELETE
    @Path("/{id}")
    public void delete(@PathParam("id") Long id) {
        throw new UnsupportedOperationException();
    }

    /**
     * Modyfikuje istniejący lot.
     * @param id identyfikator lotu, który ma zostać zmodyfikowany
     * @param flightDto dane, które mają zostać zapisane
     */
    @PUT
    @Path("/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    public void update(@PathParam("id") Long id, FlightDto flightDto) {
        throw new UnsupportedOperationException();
    }

    @GET
    @Path("/{id}/tickets")
    @Produces(MediaType.APPLICATION_JSON)
    public List<TicketDto> findTicketsForFlight(@PathParam("id") Long id) {
        throw new UnsupportedOperationException();
    }

}

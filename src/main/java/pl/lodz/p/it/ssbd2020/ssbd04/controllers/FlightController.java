package pl.lodz.p.it.ssbd2020.ssbd04.controllers;

import pl.lodz.p.it.ssbd2020.ssbd04.mob.dto.TicketDto;
import pl.lodz.p.it.ssbd2020.ssbd04.mol.dto.FlightDto;
import pl.lodz.p.it.ssbd2020.ssbd04.mol.dto.FlightQueryDto;
import pl.lodz.p.it.ssbd2020.ssbd04.mol.dto.SeatDto;
import pl.lodz.p.it.ssbd2020.ssbd04.mol.endpoints.FlightEndpoint;

import javax.inject.Inject;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
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
     * @param query kryterium
     * @return loty spełniające podane kryterium
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public List<FlightDto> find(@NotNull @Valid FlightQueryDto query) {
        throw new UnsupportedOperationException();
    }

    /**
     * Zwraca loty o podanym identyfikatorze.
     * @param id identyfikator lotu
     * @return lot o podanym identyfikatorze
     */
    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public FlightDto findById(@PathParam("id") Long id) {
        throw new UnsupportedOperationException();
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
    public FlightDto create(@NotNull @Valid FlightDto flightDto) {
        throw new UnsupportedOperationException();
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

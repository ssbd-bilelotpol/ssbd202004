package pl.lodz.p.it.ssbd2020.ssbd04.controllers;

import pl.lodz.p.it.ssbd2020.ssbd04.exceptions.AppBaseException;
import pl.lodz.p.it.ssbd2020.ssbd04.mob.dto.TicketBuyDto;
import pl.lodz.p.it.ssbd2020.ssbd04.mob.dto.TicketDto;
import pl.lodz.p.it.ssbd2020.ssbd04.mob.dto.TicketReturnDto;
import pl.lodz.p.it.ssbd2020.ssbd04.mob.dto.TicketUpdateDto;
import pl.lodz.p.it.ssbd2020.ssbd04.mob.endpoints.TicketEndpoint;

import javax.inject.Inject;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;

/**
 * Odpowiada zasobom reprezentującym logikę przetwarzania biletów.
 * Konwertuje DTO na model biznesowy oraz zajmuje się walidacją danych.
 */
@Path("/tickets")
public class TicketController extends AbstractController {

    @Inject
    private TicketEndpoint ticketEndpoint;

    /**
     * Zwraca bilet o określonym ID
     *
     * @param id identyfikator biletu
     * @return bilet o określonym ID
     */
    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public TicketDto findById(@PathParam("id") Long id) {
        throw new UnsupportedOperationException();
    }

    /**
     * Zwraca listę wszystkich biletów
     *
     * @return lista wszystkich biletów
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<TicketDto> getAllTickets() {
        throw new UnsupportedOperationException();
    }

    /**
     * Tworzy bilet o wybranych parametrach
     *
     * @param ticketDto parametry kupowanego biletu
     */
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public void buyTicket(@NotNull @Valid TicketBuyDto ticketDto) throws AppBaseException {
        repeat(ticketEndpoint, () -> ticketEndpoint.buyTicket(ticketDto));
    }

    /**
     * Zwraca zakupiony bilet
     *
     * @param ticketReturnDto parametry zwracania biletu
     */
    @DELETE
    @Path("/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    public void returnTicket(@NotNull @Valid TicketReturnDto ticketReturnDto) {
        throw new UnsupportedOperationException();
    }

    /**
     * Aktualizuje dane biletu
     *
     * @param id identyfikator biletu
     * @param ticketUpdateDto nowe dane biletu
     */
    @PUT
    @Path("/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    public void update(@PathParam("id") Long id, @NotNull @Valid TicketUpdateDto ticketUpdateDto) {
        throw new UnsupportedOperationException();
    }

}

package pl.lodz.p.it.ssbd2020.ssbd04.controllers;

import pl.lodz.p.it.ssbd2020.ssbd04.exceptions.AppBaseException;
import pl.lodz.p.it.ssbd2020.ssbd04.mob.dto.TicketBuyDto;
import pl.lodz.p.it.ssbd2020.ssbd04.mob.dto.TicketDto;
import pl.lodz.p.it.ssbd2020.ssbd04.mob.dto.TicketListDto;
import pl.lodz.p.it.ssbd2020.ssbd04.mob.dto.TicketUpdateDto;
import pl.lodz.p.it.ssbd2020.ssbd04.mob.endpoints.TicketEndpoint;
import pl.lodz.p.it.ssbd2020.ssbd04.security.EtagBinding;
import pl.lodz.p.it.ssbd2020.ssbd04.security.MessageSigner;

import javax.inject.Inject;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.time.LocalDateTime;
import java.util.List;

/**
 * Odpowiada zasobom reprezentującym logikę przetwarzania biletów.
 * Konwertuje DTO na model biznesowy oraz zajmuje się walidacją danych.
 */
@Path("/tickets")
public class TicketController extends AbstractController {

    @Inject
    private TicketEndpoint ticketEndpoint;

    @Inject
    private MessageSigner messageSigner;

    /**
     * Zwraca bilet o określonym ID
     *
     * @param id identyfikator biletu
     * @return bilet o określonym ID
     * @throws AppBaseException gdy operacja się nie powiedzie
     */
    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response findById(@PathParam("id") Long id) throws AppBaseException {
        TicketDto ticketDto = ticketEndpoint.findById(id);
        return Response
                .ok()
                .entity(ticketDto)
                .tag(messageSigner.sign(ticketDto))
                .build();
    }

    /**
     * Wyszukuje bilety na podstawie przekazanego kryterium.
     *
     * @param code         kod lotu
     * @param connectionId id połączenia
     * @param airplaneId   id lotniska
     * @param from         data, po której wylatuje lot
     * @param to           dat, przed którą wylatuje lot
     * @return loty spełniające podane kryterium
     * @throws AppBaseException gdy operacja się nie powiodła
     */
    @GET
    @Path("/list")
    @Produces(MediaType.APPLICATION_JSON)
    public List<TicketListDto> find(@QueryParam("code") String code, @QueryParam("connection") Long connectionId,
                                    @QueryParam("airplane") Long airplaneId, @QueryParam("from") LocalDateTime from,
                                    @QueryParam("to") LocalDateTime to) throws AppBaseException {
        return repeat(ticketEndpoint, () -> ticketEndpoint.find(code, connectionId, airplaneId, from, to));
    }

    /**
     * Tworzy bilet o wybranych parametrach
     *
     * @param ticketDto parametry kupowanego biletu
     * @throws AppBaseException gdy operacja się nie powiedzie
     */
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public void buyTicket(@NotNull @Valid TicketBuyDto ticketDto) throws AppBaseException {
        repeat(ticketEndpoint, () -> ticketEndpoint.buyTicket(ticketDto));
    }

    /**
     * Zwraca zakupiony bilet
     *
     * @param id identyfikator zwracanego biletu
     * @throws AppBaseException gdy operacja się nie powiedzie
     */
    @DELETE
    @Path("/{id}")
    public void returnTicket(@PathParam("id") Long id) throws AppBaseException {
        repeat(ticketEndpoint, () -> ticketEndpoint.returnTicket(id));
    }

    /**
     * Aktualizuje dane biletu
     *
     * @param id              identyfikator biletu
     * @param ticketUpdateDto nowe dane biletu
     * @throws AppBaseException gdy operacja się nie powiedzie
     */
    @PUT
    @Path("/{id}")
    @EtagBinding
    public void update(@PathParam("id") Long id, @NotNull @Valid TicketUpdateDto ticketUpdateDto) throws AppBaseException {
        repeat(ticketEndpoint, () -> ticketEndpoint.update(id, ticketUpdateDto));
    }

}

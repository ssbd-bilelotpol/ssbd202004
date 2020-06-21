package pl.lodz.p.it.ssbd2020.ssbd04.controllers;

import pl.lodz.p.it.ssbd2020.ssbd04.entities.FlightStatus;
import pl.lodz.p.it.ssbd2020.ssbd04.exceptions.AppBaseException;
import pl.lodz.p.it.ssbd2020.ssbd04.mob.endpoints.TicketEndpoint;
import pl.lodz.p.it.ssbd2020.ssbd04.mol.dto.FlightCreateDto;
import pl.lodz.p.it.ssbd2020.ssbd04.mol.dto.FlightDto;
import pl.lodz.p.it.ssbd2020.ssbd04.mol.dto.FlightEditDto;
import pl.lodz.p.it.ssbd2020.ssbd04.mol.dto.SeatDto;
import pl.lodz.p.it.ssbd2020.ssbd04.mol.endpoints.FlightEndpoint;
import pl.lodz.p.it.ssbd2020.ssbd04.security.EtagBinding;
import pl.lodz.p.it.ssbd2020.ssbd04.security.MessageSigner;
import pl.lodz.p.it.ssbd2020.ssbd04.validation.FlightCode;

import javax.inject.Inject;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.time.LocalDate;
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
    @Inject
    private MessageSigner messageSigner;


    @Inject
    private TicketEndpoint ticketEndpoint;

    /**
     * Wyszukuje loty na podstawie przekazanego kryterium.
     * @param code kod lotu
     * @param connectionId id połączenia
     * @param airplaneId id samolotu
     * @param from data, po której wylatuje lot
     * @param to dat, przed którą wylatuje lot
     * @return loty spełniające podane kryterium
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<FlightDto> find(@QueryParam("code") String code, @QueryParam("connection") Long connectionId,
                                @QueryParam("airplane") Long airplaneId, @QueryParam("from") LocalDateTime from,
                                @QueryParam("to") LocalDateTime to, @QueryParam("status") FlightStatus status)
            throws AppBaseException {
        return repeat(flightEndpoint, () -> flightEndpoint.find(code, connectionId, airplaneId, from, to, status));
    }

    @GET
    @Path("/dates")
    @Produces(MediaType.APPLICATION_JSON)
    public List<LocalDate> getDates(@QueryParam("from") LocalDateTime from) throws AppBaseException {
        return repeat(flightEndpoint, () -> flightEndpoint.getDates(from));
    }

    /**
     * Zwraca loty o podanym identyfikatorze.
     * @param code identyfikator lotu
     * @return lot o podanym identyfikatorze
     */
    @GET
    @Path("/{code}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response findByCode(@PathParam("code") @Valid @FlightCode String code) throws AppBaseException {
        FlightDto flightDto = repeat(flightEndpoint, () -> flightEndpoint.findByCode(code));
        return Response
                .ok()
                .entity(flightDto)
                .tag(messageSigner.sign(flightDto))
                .build();
    }

    @GET
    @Path("/{id}/taken-seats")
    @Produces(MediaType.APPLICATION_JSON)
    public List<SeatDto> getTakenSeats(@PathParam("id") String id) throws AppBaseException {
        return repeat(flightEndpoint, () -> flightEndpoint.getTakenSeats(id));
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
     * @param code identyfikator lotu do usunięcia.
     */
    @DELETE
    @EtagBinding
    @Path("/{code}")
    public void cancel(@PathParam("code") String code) throws AppBaseException {
        repeat(flightEndpoint, () -> flightEndpoint.cancel(code));
    }

    /**
     * Modyfikuje istniejący lot.
     * @param code identyfikator lotu, który ma zostać zmodyfikowany
     * @param flightDto dane, które mają zostać zapisane
     */
    @PUT
    @EtagBinding
    @Path("/{code}")
    @Consumes(MediaType.APPLICATION_JSON)
    public void update(@PathParam("code") String code, FlightEditDto flightDto) throws AppBaseException {
        repeat(flightEndpoint, () -> flightEndpoint.update(code, flightDto));
    }
}

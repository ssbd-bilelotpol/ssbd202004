package pl.lodz.p.it.ssbd2020.ssbd04.controllers;

import pl.lodz.p.it.ssbd2020.ssbd04.exceptions.AppBaseException;
import pl.lodz.p.it.ssbd2020.ssbd04.mob.dto.PassengerDto;
import pl.lodz.p.it.ssbd2020.ssbd04.mob.endpoints.PassengerEndpoint;

import javax.annotation.security.PermitAll;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import java.util.List;

/**
 * Odpowiada zasobom reprezentującym logikę przetwarzania kont.
 * Konwertuje DTO na model biznesowy oraz zajmuje się walidacją danych.
 */
@Path("/clients")
public class PassengerController extends AbstractController {
    @Inject
    PassengerEndpoint passengerEndpoint;

    /**
     * Wyszukuje pasażerów na podstawie przekazanego kryterium.
     *
     * @param flightCode kod lotniska.
     * @param name       imię i nazwisko pasażera.
     * @return lotniska spełniające podane kryterium.
     * @throws AppBaseException gdy operacja się nie powiedzie
     */
    @GET
    @Produces({MediaType.APPLICATION_JSON})
    @PermitAll
    public List<PassengerDto> getClients(@QueryParam("name") String name, @QueryParam("flightCode") String flightCode) throws AppBaseException {
        return repeat(passengerEndpoint, () -> passengerEndpoint.find(name, flightCode));
    }

}

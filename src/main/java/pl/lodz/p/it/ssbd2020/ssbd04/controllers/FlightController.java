package pl.lodz.p.it.ssbd2020.ssbd04.controllers;

import javax.ws.rs.POST;
import javax.ws.rs.Path;

/**
 * Odpowiada zasobom reprezentującym logikę przetwarzania lotów.
 * Konwertuje DTO na model biznesowy oraz zajmuje się walidacją danych.
 */
@Path("/flights")
public class FlightController extends AbstractController {
    @POST
    public void pong() {
        //TODO: delete as soon as the first method is implemented
        throw new UnsupportedOperationException();
    }
}

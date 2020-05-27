package pl.lodz.p.it.ssbd2020.ssbd04.controllers;

import pl.lodz.p.it.ssbd2020.ssbd04.mob.dto.ReportDto;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

/**
 * Odpowiada zasobom reprezentującym logikę przetwarzania raportów.
 * Konwertuje DTO na model biznesowy oraz zajmuje się walidacją danych.
 */
@Path("/reports")
public class ReportsController extends AbstractController {

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public ReportDto generateReport() {
        throw new UnsupportedOperationException();
    }

}

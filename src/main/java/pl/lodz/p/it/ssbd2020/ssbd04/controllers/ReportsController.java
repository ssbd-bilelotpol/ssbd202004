package pl.lodz.p.it.ssbd2020.ssbd04.controllers;

import pl.lodz.p.it.ssbd2020.ssbd04.exceptions.AppBaseException;
import pl.lodz.p.it.ssbd2020.ssbd04.mob.dto.ReportDto;
import pl.lodz.p.it.ssbd2020.ssbd04.mob.endpoints.TicketEndpoint;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.List;

/**
 * Odpowiada zasobom reprezentującym logikę przetwarzania raportów.
 * Konwertuje DTO na model biznesowy oraz zajmuje się walidacją danych.
 */
@Path("/reports")
public class ReportsController extends AbstractController {

    @Inject
    private TicketEndpoint ticketEndpoint;

    /**
     * Zwraca listę z informacjami o zyskach na danych połączeniach
     * @return lista z informacjami o zyskach na danych połączeniach
     * @throws AppBaseException gdy operacja się nie powiedzie
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<ReportDto> generateReport() throws AppBaseException {
        return repeat(ticketEndpoint, () -> ticketEndpoint.generateReport());
    }

}

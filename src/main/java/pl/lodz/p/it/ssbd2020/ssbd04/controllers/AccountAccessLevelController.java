package pl.lodz.p.it.ssbd2020.ssbd04.controllers;

import pl.lodz.p.it.ssbd2020.ssbd04.exceptions.AppBaseException;
import pl.lodz.p.it.ssbd2020.ssbd04.mok.dto.AccountAccessLevelDto;
import pl.lodz.p.it.ssbd2020.ssbd04.mok.endpoints.AccountEndpoint;
import pl.lodz.p.it.ssbd2020.ssbd04.security.EtagBinding;
import pl.lodz.p.it.ssbd2020.ssbd04.security.MessageSigner;
import pl.lodz.p.it.ssbd2020.ssbd04.validation.Login;

import javax.inject.Inject;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * Odpowiada zasobom reprezentującym logikę przetwarzania poziomów dostępu kont.
 * Konwertuje DTO na model biznesowy oraz zajmuje się walidacją danych.
 */
@Path("/accounts/{login}/access-levels")
public class AccountAccessLevelController extends AbstractController {
    @Inject
    private AccountEndpoint accountEndpoint;

    @Inject
    private MessageSigner messageSigner;

    /**
     * Pobiera zbiór poziomów dostępu dla konta.
     *
     * @param login nazwa użytkownika.
     * @return lista poziomów dostępu.
     * @throws AppBaseException gdy operacja się nie powiedzie.
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getRoles(@PathParam("login") @NotNull @Login @Valid String login) throws AppBaseException {
        AccountAccessLevelDto accountAccessLevelDto = repeat(accountEndpoint, () -> accountEndpoint.getAccessLevels(login));
        return Response.ok()
                .entity(accountAccessLevelDto)
                .tag(messageSigner.sign(accountAccessLevelDto))
                .build();
    }

    /**
     * Edytuje zbiór poziomów dostępu konta.
     *
     * @param login                 nazwa użytkownika.
     * @param accountAccessLevelDto reprezentuje zbiór poziomów dostępu.
     * @return Zaktualizowany zbiór poziomów dostępu konta.
     * @throws AppBaseException gdy operacja się nie powiedzie.
     */
    @EtagBinding
    @PUT
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response assignRole(@PathParam("login") @NotNull @Login @Valid String login, @NotNull @Valid AccountAccessLevelDto accountAccessLevelDto) throws AppBaseException {
        final AccountAccessLevelDto accessLevelDto = repeat(accountEndpoint, () -> accountEndpoint.editAccountAccessLevel(login, accountAccessLevelDto));
        return Response.ok()
                .entity(accessLevelDto)
                .tag(messageSigner.sign(accessLevelDto))
                .build();
    }
}

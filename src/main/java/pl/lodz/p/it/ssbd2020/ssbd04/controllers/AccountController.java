package pl.lodz.p.it.ssbd2020.ssbd04.controllers;

import pl.lodz.p.it.ssbd2020.ssbd04.exceptions.AppBaseException;
import pl.lodz.p.it.ssbd2020.ssbd04.mok.dto.AccountDto;
import pl.lodz.p.it.ssbd2020.ssbd04.mok.dto.AccountEditDto;
import pl.lodz.p.it.ssbd2020.ssbd04.mok.dto.AccountRegisterDto;
import pl.lodz.p.it.ssbd2020.ssbd04.mok.endpoints.AccountEndpoint;
import pl.lodz.p.it.ssbd2020.ssbd04.mok.entities.Account;
import pl.lodz.p.it.ssbd2020.ssbd04.mok.entities.AccountDetails;
import pl.lodz.p.it.ssbd2020.ssbd04.security.MessageSigner;
import pl.lodz.p.it.ssbd2020.ssbd04.security.Role;
import pl.lodz.p.it.ssbd2020.ssbd04.utils.EtagBinding;
import pl.lodz.p.it.ssbd2020.ssbd04.utils.VUUID;

import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;
import javax.inject.Inject;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.UUID;

/**
 * Odpowiada zasobom reprezentującym logikę przetwarzania kont.
 * Konwertuje DTO na model biznesowy oraz zajmuje się walidacją danych.
 */
@Path("/accounts")
@PermitAll
public class AccountController {

    @Inject
    private AccountEndpoint accountEndpoint;

    @Inject
    private MessageSigner messageSigner;

    /**
     * Rejestruje nowe konto
     *
     * @param accountRegisterDto
     * @throws AppBaseException
     */
    @POST
    public void register(@NotNull @Valid AccountRegisterDto accountRegisterDto) throws AppBaseException {
        Account account = new Account();
        account.setLogin(accountRegisterDto.getLogin());
        account.setPassword(accountRegisterDto.getPassword());

        AccountDetails accountDetails = new AccountDetails();
        accountDetails.setEmail(accountRegisterDto.getEmail());
        accountDetails.setFirstName(accountRegisterDto.getFirstName());
        accountDetails.setLastName(accountRegisterDto.getLastName());
        accountDetails.setPhoneNumber(accountRegisterDto.getPhoneNumber());

        accountEndpoint.register(account, accountDetails);
    }

    /**
     * Potwierdza nowo zarejestrowane konto na podstawie żetonu.
     *
     * @param tokenId
     * @throws AppBaseException
     */
    @POST
    @Path("/confirm/{tokenId}")
    public void confirm(@NotNull @VUUID @Valid @PathParam("tokenId") String tokenId) throws AppBaseException {
        accountEndpoint.confirm(UUID.fromString(tokenId));
    }

    /**
     * Zwraca dane konta inicjującego żądanie
     *
     * @return konto inicjujące żądanie
     * @throws AppBaseException gdy nie udało się pobrać danych konta
     */
    @GET
    @Path("/self")
    @Produces(MediaType.APPLICATION_JSON)
    @RolesAllowed(Role.Client)
    public Response retrieveOwnAccountDetails() throws AppBaseException {
        AccountDto accountDto = accountEndpoint.retrieveOwnAccountDetails();
        return Response.ok()
                .entity(accountDto)
                .tag(messageSigner.sign(accountDto))
                .build();
    }

    /**
     * Zwraca dane konta o wybranym loginie.
     *
     * @param login login konta, którego dane zostaną zwrócone.
     * @return konto wybranego użytkownika.
     * @throws AppBaseException gdy nie udało się pobrać danych konta.
     */
    @GET
    @Path("/{login}")
    @Produces(MediaType.APPLICATION_JSON)
    @RolesAllowed(Role.Admin)
    public Response retrieveOtherAccountDetails(@PathParam("login") String login) throws AppBaseException {
        AccountDto accountDto = accountEndpoint.retrieveOtherAccountDetails(login);
        return Response.ok()
                .entity(accountDto)
                .tag(messageSigner.sign(accountDto))
                .build();
    }

    /**
     * Modyfikuje dane szczegółowe konta inicjującego żądanie.
     *
     * @param accountEditDto nowe dane konta w których skład wchodzi jedynie imie, nazwisko oraz numer telefonu.
     * @return konto inicjujące żądanie z uwzględnionymi zmianami danych.
     * @throws AppBaseException gdy zapisanie zmodyfikowanego konta nie powiodło się.
     */
    @PUT
    @Path("/self")
    @EtagBinding
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @RolesAllowed(Role.Client)
    public Response editOwnAccountDetails(@NotNull @Valid AccountEditDto accountEditDto) throws AppBaseException {
        AccountDto accountDto = accountEndpoint.editOwnAccountDetails(accountEditDto);
        return Response.ok()
                .entity(accountDto)
                .tag(messageSigner.sign(accountDto))
                .build();
    }

    /**
     * Modyfikuje dane szczegółowe wybranego konta.
     *
     * @param login          login konta, którego dane zostaną zmodyfikowane.
     * @param accountEditDto nowe dane konta w których skład wchodzi jedynie imie, nazwisko oraz numer telefonu.
     * @return wybrane konto z uwzględnionymi zmianami danych.
     * @throws AppBaseException gdy zapisanie zmodyfikowanego konta nie powiodło się.
     */
    @PUT
    @Path("/{login}")
    @EtagBinding
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @RolesAllowed(Role.Admin)
    public Response editOtherAccountDetails(@PathParam("login") String login, @NotNull @Valid AccountEditDto accountEditDto) throws AppBaseException {
        AccountDto accountDto = accountEndpoint.editOtherAccountDetails(login, accountEditDto);
        return Response.ok()
                .entity(accountDto)
                .tag(messageSigner.sign(accountDto))
                .build();
    }

}

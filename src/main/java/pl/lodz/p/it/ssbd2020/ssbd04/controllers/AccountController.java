package pl.lodz.p.it.ssbd2020.ssbd04.controllers;

import pl.lodz.p.it.ssbd2020.ssbd04.exceptions.AppBaseException;
import pl.lodz.p.it.ssbd2020.ssbd04.mok.dto.AccountRegisterDto;
import pl.lodz.p.it.ssbd2020.ssbd04.mok.endpoints.AccountEndpoint;
import pl.lodz.p.it.ssbd2020.ssbd04.mok.entities.Account;
import pl.lodz.p.it.ssbd2020.ssbd04.mok.entities.AccountDetails;
import pl.lodz.p.it.ssbd2020.ssbd04.utils.VUUID;

import javax.annotation.security.PermitAll;
import javax.inject.Inject;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
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
}

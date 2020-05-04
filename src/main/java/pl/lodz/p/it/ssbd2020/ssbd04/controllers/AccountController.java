package pl.lodz.p.it.ssbd2020.ssbd04.controllers;

import pl.lodz.p.it.ssbd2020.ssbd04.exceptions.AccountException;
import pl.lodz.p.it.ssbd2020.ssbd04.exceptions.AppBaseException;
import pl.lodz.p.it.ssbd2020.ssbd04.mok.dto.*;
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
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;
import java.util.UUID;
import java.util.logging.Logger;

/**
 * Odpowiada zasobom reprezentującym logikę przetwarzania kont.
 * Konwertuje DTO na model biznesowy oraz zajmuje się walidacją danych.
 */
@Path("/accounts")
@PermitAll
public class AccountController {
    private static final Logger LOGGER = Logger.getLogger(AccountController.class.getName());

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
     * Zwraca listę wszystkich kont wraz z ich danymi szczegółowymi.
     *
     * @return lista konta wraz z danymi szczegółowymi.
     */
    @GET
    @Produces({MediaType.APPLICATION_JSON})
    @RolesAllowed(Role.Admin)
    public List<AccountDto> getAllAccounts() {
        return accountEndpoint.getAllAccounts();
    }

    /**
     * Zwraca zbiór wszystkich kont wraz z ich danymi o ostatnim uwierzytelnieniu.
     *
     * @return zbiór z danymi o ostatnim uwierzytelnieniu.
     */
    @GET
    @Produces({MediaType.APPLICATION_JSON})
    @RolesAllowed(Role.Admin)
    @Path("/auth-report")
    public List<AccountAuthInfoDto> getAllAccountsAuthInfo() {
        return accountEndpoint.getAllAccountsAuthInfo();
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

    /**
     * Jeżeli użytkownik o podanym e-mailu istnieje, to wysyła do niego e-mail resetujący hasło.
     * Jeżeli nie istnieje to nic się nie dzieje, a metoda nie wyrzuca z tego powodu wyjątku.
     *
     * @param email e-mail użytkownika
     * @throws AppBaseException w przypadku innych błędów niż brak użytkownika z podanym e-mailem
     */
    @POST
    @Path("/{email}/password/reset")
    public void requestPasswordReset(@NotNull @Valid @Email @PathParam("email") String email) throws AppBaseException {
        try {
            accountEndpoint.sendResetPasswordToken(email);
        } catch (AccountException e) {
            LOGGER.info(e.toString());
        }
    }

    /**
     * Ustawia nowe hasło dla użytkownika przypisanego do przekazywanego tokenu
     *
     * @param passwordResetDto nowe hasło oraz token do resetowania
     * @throws AppBaseException gdy operacja się nie powiedzie
     */
    @POST
    @Path("/password/reset")
    @Consumes(MediaType.APPLICATION_JSON)
    public void resetPassword(@Valid PasswordResetDto passwordResetDto) throws AppBaseException {
        accountEndpoint.resetPassword(passwordResetDto);
    }

    /**
     * Zmienia hasło dla aktualnego użytkownika. Zwraca kod 204 jeśli operacja się powiedzie.
     *
     * @param accountPasswordDto obiekt który przechowuje nowe i stare hasła podane przez użytkownika.
     * @throws AppBaseException gdy podane przez użytkownika hasła się nie zgadzają, lub Etag się nie zgadza.
     */
    @PUT
    @Path("/self/password")
    @EtagBinding
    @Consumes(MediaType.APPLICATION_JSON)
    public void changeOwnPassword(@NotNull @Valid AccountPasswordDto accountPasswordDto) throws AppBaseException {
        accountEndpoint.changeOwnAccountPassword(accountPasswordDto);
    }


    /**
     * Zmienia hasło dla podanego użytkownika. Zwraca kod 204 jeśli operacja się powiedzie.
     *
     * @param login              login konta, dla którego zmieniamy hasło.
     * @param accountPasswordDto obiekt reprezentujący formularz zmiany hasła (atrybut oldPassword może być null).
     * @throws AppBaseException jeśli wartość Etaga nie będzie się zgadzać.
     */
    @PUT
    @Path("/{login}/password")
    @EtagBinding
    @Consumes(MediaType.APPLICATION_JSON)
    public void changeAccountPassword(@NotNull @PathParam("login") String login, AccountPasswordDto accountPasswordDto) throws AppBaseException {
        accountEndpoint.changeOtherAccountPassword(login, accountPasswordDto.getNewPassword());
    }
}

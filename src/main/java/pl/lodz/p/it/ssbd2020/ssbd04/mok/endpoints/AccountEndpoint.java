package pl.lodz.p.it.ssbd2020.ssbd04.mok.endpoints;

import at.favre.lib.crypto.bcrypt.BCrypt;
import pl.lodz.p.it.ssbd2020.ssbd04.common.AbstractEndpoint;
import pl.lodz.p.it.ssbd2020.ssbd04.entities.Account;
import pl.lodz.p.it.ssbd2020.ssbd04.entities.AccountDetails;
import pl.lodz.p.it.ssbd2020.ssbd04.exceptions.AccountException;
import pl.lodz.p.it.ssbd2020.ssbd04.exceptions.AppBaseException;
import pl.lodz.p.it.ssbd2020.ssbd04.interceptors.TrackingInterceptor;
import pl.lodz.p.it.ssbd2020.ssbd04.mok.dto.*;
import pl.lodz.p.it.ssbd2020.ssbd04.mok.services.AccountService;
import pl.lodz.p.it.ssbd2020.ssbd04.mok.services.VerificationTokenService;
import pl.lodz.p.it.ssbd2020.ssbd04.security.AuthContext;

import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;
import javax.ejb.Stateful;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;
import javax.interceptor.Interceptors;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import static pl.lodz.p.it.ssbd2020.ssbd04.security.Role.*;

/**
 * Wykonuje konwersję klas DTO na model biznesowy
 * i jest granicą transakcji aplikacyjnej dla hierarchii klas Account i AccountAccessLevel.
 */

@Interceptors({TrackingInterceptor.class})
@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
@Stateful
public class AccountEndpoint extends AbstractEndpoint {

    @Inject
    private AccountService accountService;

    @Inject
    private VerificationTokenService tokenService;

    @Inject
    private AuthContext auth;

    @PermitAll
    public void register(Account account, AccountDetails accountDetails) throws AppBaseException {
        accountService.register(account, accountDetails);
    }

    @PermitAll
    public void confirm(UUID fromString) throws AppBaseException {
        accountService.confirm(fromString);
    }

    @RolesAllowed(EditAccountAccessLevel)
    public AccountAccessLevelDto editAccountAccessLevel(String login, AccountAccessLevelDto accountAccessLevelDto) throws AppBaseException {
        Account account = accountService.findByLogin(login);
        AccountAccessLevelDto currentAccountAccessLevelDto = new AccountAccessLevelDto(account);
        if (!verifyEtag(currentAccountAccessLevelDto)) {
            throw AppBaseException.optimisticLock();
        }

        accountService.editAccountAccessLevel(account, accountAccessLevelDto.toAccountAccessLevelSet());
        return new AccountAccessLevelDto(account);
    }

    @RolesAllowed(GetAccessLevels)
    public AccountAccessLevelDto getAccessLevels(String login) throws AppBaseException {
        return new AccountAccessLevelDto(accountService.findByLogin(login));
    }

    /**
     * Zwraca dane konta inicjującego żądanie.
     *
     * @return konto inicjujące żądanie.
     * @throws AppBaseException gdy nie udało się pobrać danych konta.
     */
    @RolesAllowed(RetrieveOwnAccountDetails)
    public AccountDto retrieveOwnAccountDetails() throws AppBaseException {
        return new AccountDto(auth.currentUser());
    }

    /**
     * Zwraca dane konta o wybranym loginie.
     *
     * @param login login konta, którego dane zostaną zwrócone.
     * @return konto wybranego użytkownika.
     * @throws AppBaseException gdy nie udało się pobrać danych konta.
     */
    @RolesAllowed(RetrieveOtherAccountDetails)
    public AccountDto retrieveOtherAccountDetails(String login) throws AppBaseException {
        Account account = accountService.findByLogin(login);
        return new AccountDto(account);
    }

    /**
     * Modyfikuje dane szczegółowe konta inicjującego żądanie.
     *
     * @param accountEditDto nowe dane konta w których skład wchodzi jedynie imie, nazwisko oraz numer telefonu.
     * @return konto inicjujące żądanie z uwzględnionymi zmianami danych.
     * @throws AppBaseException gdy zapisanie zmodyfikowanego konta nie powiodło się.
     */
    @RolesAllowed(EditOwnAccountDetails)
    public AccountDto editOwnAccountDetails(AccountEditDto accountEditDto) throws AppBaseException {
        Account account = auth.currentUser();
        AccountDto currentAccountDto = new AccountDto(account);
        if (!verifyEtag(currentAccountDto)) {
            throw AppBaseException.optimisticLock();
        }

        return editAccountDetails(account, accountEditDto);
    }

    /**
     * Modyfikuje dane szczegółowe wybranego konta.
     *
     * @param login          login konta, którego dane zostaną zmodyfikowane.
     * @param accountEditDto nowe dane konta w których skład wchodzi jedynie imie, nazwisko oraz numer telefonu.
     * @return wybrane konto z uwzględnionymi zmianami danych.
     * @throws AppBaseException gdy zapisanie zmodyfikowanego konta nie powiodło się.
     */
    @RolesAllowed(EditOtherAccountDetails)
    public AccountDto editOtherAccountDetails(String login, AccountEditDto accountEditDto) throws AppBaseException {
        Account account = accountService.findByLogin(login);
        AccountDto currentAccountDto = new AccountDto(account);
        if (!verifyEtag(currentAccountDto)) {
            throw AppBaseException.optimisticLock();
        }

        return editAccountDetails(account, accountEditDto);
    }

    private AccountDto editAccountDetails(Account account, AccountEditDto accountEditDto) throws AppBaseException {
        AccountDetails newDetails = new AccountDetails(
                accountEditDto.getFirstName(),
                accountEditDto.getLastName(),
                account.getAccountDetails().getEmail(),
                accountEditDto.getPhoneNumber()
        );

        Account editedAccount = accountService.editAccountDetails(account, newDetails);
        return new AccountDto(editedAccount);
    }

    /**
     * Wysyła token resetujący hasło użytkownika o podanym emailu.
     * <p>
     * Użytkownik musi być aktywny, a jego rejestracja potwierdzona.
     *
     * @param email e-mail użytkownika.
     * @throws AppBaseException w przypadku niepowodzenia operacji.
     */
    @PermitAll
    public void sendResetPasswordToken(String email) throws AppBaseException {
        Account account = accountService.findByEmail(email);
        if (!account.getActive())
            throw AccountException.notActive(account);
        if (!account.getConfirm())
            throw AccountException.notConfirmed(account);
        tokenService.sendResetPasswordToken(account);
    }

    /**
     * Resetuje hasło za pomocą tokenu resetującego.
     *
     * @param passwordResetDto token resetujący oraz nowe hasło.
     * @throws AppBaseException w przypadku niepowodzenia operacji.
     */
    @PermitAll
    public void resetPassword(PasswordResetDto passwordResetDto) throws AppBaseException {
        accountService.resetPassword(passwordResetDto);
    }

    /**
     * Aktualizuje dane o ostatnim poprawnym uwierzytelnieniu użytkownika.
     *
     * @param login         login użytkownika.
     * @param lastIpAddress adres ip.
     * @param currentAuth   data logowania.
     * @throws AppBaseException
     */
    @PermitAll
    public void updateAuthInfo(String login, String lastIpAddress, LocalDateTime currentAuth) throws AppBaseException {
        accountService.updateAuthInfo(login, lastIpAddress, currentAuth);
    }

    /**
     * Aktualizuje dane o ostatnim niepoprawnym uwierzytelnieniu użytkownika.
     *
     * @param username          login użytkownika.
     * @param lastIncorrectAuth data logowania.
     */
    @PermitAll
    public void updateAuthInfo(String username, LocalDateTime lastIncorrectAuth) throws AppBaseException {
        accountService.updateAuthInfo(username, lastIncorrectAuth);
    }

    /**
     * Zwraca zbiór wszystkich kont wraz z ich danymi ostatniego uwierzytelniania.
     *
     * @return dane uwierzytelniania.
     */
    @RolesAllowed(GetAllAccountsAuthInfo)
    public List<AccountAuthInfoDto> getAllAccountsAuthInfo() {
        return accountService.getAll().stream()
                .map(a -> new AccountAuthInfoDto(a.getLogin(),
                        a.getAccountAuthInfo().getCurrentAuth(),
                        a.getAccountAuthInfo().getLastIpAddress())).collect(Collectors.toList());
    }

    /**
     * Zwraca dane z ostatniego uwierzytelniania dla konta.
     *
     * @return
     */
    @RolesAllowed(GetAccountAuthInfo)
    public AccountAuthInfoDto getAccountAuthInfo() throws AppBaseException {
        Account account = auth.currentUser();
        return new AccountAuthInfoDto(account.getAccountAuthInfo().getLastSuccessAuth(),
                account.getAccountAuthInfo().getLastIncorrectAuth());
    }

    /**
     * Zmienia hasło dla aktualnego użytkownika.
     *
     * @param accountPasswordDto obiekt który przechowuje nowe i stare hasła podane przez użytkownika.
     * @throws AppBaseException jeśli Etag się nie zgadza, lub podane stare hasło nie jest zgodne z tym z bazy danych.
     */
    @RolesAllowed(ChangeOwnAccountPassword)
    public void changeOwnAccountPassword(AccountPasswordDto accountPasswordDto) throws AppBaseException {
        Account account = auth.currentUser();
        AccountDto accountDto = new AccountDto(account);

        if (!verifyEtag(accountDto)) throw AppBaseException.optimisticLock();

        if (!BCrypt.verifyer()
                .verify(accountPasswordDto.getOldPassword().toCharArray(), account.getPassword()
                        .toCharArray()).verified) throw AccountException.passwordsDontMatch(account);

        accountService.changePassword(account, accountPasswordDto.getNewPassword());
    }

    /**
     * Zmienia hasło dla podanego użytkonika.
     *
     * @param login    login konta, dla którego zmieniane jest hasło.
     * @param password nowe hasło.
     * @throws AppBaseException jeśli Etag się nie zgadza.
     */
    @RolesAllowed(ChangeOtherAccountPassword)
    public void changeOtherAccountPassword(String login, String password) throws AppBaseException {
        Account account = accountService.findByLogin(login);
        AccountDto accountDto = new AccountDto(account);

        if (!verifyEtag(accountDto)) throw AppBaseException.optimisticLock();

        accountService.changePassword(account, password);
    }

    /**
     * Zwraca listę wszystkich kont wraz z ich danymi szczegółowymi, dla których imię i nazwisko jest zgodne z podaną frazą.
     *
     * @param name fraza, której poszukujemy. Jeśli name jest pustym ciągiem znaków lub null, to metoda zwraca wszystkie konta.
     * @return lista kont wraz z danymi szczegółowymi.
     * @throws AppBaseException gdy nie udało się znaleźć żadnego konta zgodnego z podaną frazą.
     */
    @RolesAllowed(FindAccountsByName)
    public List<AccountDto> findByName(String name) throws AppBaseException {
        return accountService.findByName(name);
    }

    /**
     * Zmienia status aktywności dla konta o podanym loginie.
     *
     * @param login  login konta, dla którego zmieniamy status aktywności.
     * @param active wartość statusu aktywności konta, która ma zostać ustawiona.
     * @throws AppBaseException gdy nie udało się zmienić statusu aktywności konta.
     */
    @RolesAllowed(ChangeAccountActiveStatus)
    public void changeAccountActiveStatus(String login, Boolean active) throws AppBaseException {
        accountService.changeAccountActiveStatus(login, active);
    }

}

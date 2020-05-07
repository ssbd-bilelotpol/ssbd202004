package pl.lodz.p.it.ssbd2020.ssbd04.mok.services;

import at.favre.lib.crypto.bcrypt.BCrypt;
import pl.lodz.p.it.ssbd2020.ssbd04.entities.Account;
import pl.lodz.p.it.ssbd2020.ssbd04.entities.AccountAuthInfo;
import pl.lodz.p.it.ssbd2020.ssbd04.entities.AccountDetails;
import pl.lodz.p.it.ssbd2020.ssbd04.entities.access_levels.AccountAccessLevel;
import pl.lodz.p.it.ssbd2020.ssbd04.entities.access_levels.ClientAccessLevel;
import pl.lodz.p.it.ssbd2020.ssbd04.exceptions.AccountException;
import pl.lodz.p.it.ssbd2020.ssbd04.exceptions.AppBaseException;
import pl.lodz.p.it.ssbd2020.ssbd04.interceptors.TrackingInterceptor;
import pl.lodz.p.it.ssbd2020.ssbd04.mok.dto.PasswordResetDto;
import pl.lodz.p.it.ssbd2020.ssbd04.mok.facades.AccountFacade;
import pl.lodz.p.it.ssbd2020.ssbd04.security.Role;

import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;
import javax.interceptor.Interceptors;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import static java.util.Collections.singleton;

/**
 * Przetwarzanie logiki biznesowej Kont.
 */

@Interceptors({TrackingInterceptor.class})
@Stateless
@TransactionAttribute(TransactionAttributeType.MANDATORY)
@RolesAllowed(Role.Admin)
public class AccountService {

    @Inject
    private AccountFacade accountFacade;

    @Inject
    private VerificationTokenService verificationTokenService;

    /**
     * Rejestruje konto, przypisując do niego dane personalne i wysyła żeton potwierdzający na e-mail.
     *
     * @param account
     * @param accountDetails
     * @throws AppBaseException
     */
    @PermitAll
    public void register(Account account, AccountDetails accountDetails) throws AppBaseException {
        account.setPassword(BCrypt.withDefaults().hashToString(12, account.getPassword().toCharArray()));
        account.setActive(true);
        account.setConfirm(false);
        account.setAccountAccessLevel(singleton(new ClientAccessLevel()));
        account.setAccountDetails(accountDetails);
        account.setAccountAuthInfo(new AccountAuthInfo(account));
        accountFacade.create(account);
        verificationTokenService.sendRegisterToken(account);
    }

    /**
     * Potwierdza konto na podstawie otrzymanego żetonu.
     *
     * @param tokenId
     * @throws AppBaseException
     */
    @PermitAll
    public void confirm(UUID tokenId) throws AppBaseException {
        Account account = verificationTokenService.confirmRegistration(tokenId);
        account.setConfirm(true);
        accountFacade.edit(account);
    }

    @PermitAll
    public Account findByLogin(String login) throws AppBaseException {
        return accountFacade.findByLogin(login);
    }

    public void editAccountAccessLevel(Account account, Set<AccountAccessLevel> accountAccessLevels) throws AppBaseException {
        account.getAccountAccessLevel().retainAll(accountAccessLevels);
        account.getAccountAccessLevel().addAll(accountAccessLevels);
        accountFacade.edit(account);
    }

    /**
     * Modyfikuje dane szczegółowe wybranego konta.
     *
     * @param account    konto dla którego dane są zmieniane.
     * @param newDetails nowe dane szczegółowe w których skład wchodzi jedynie imie, nazwisko oraz numer telefonu.
     * @return konto z uwzględnioną zmianą danych szczegółowych.
     * @throws AppBaseException gdy zapisanie zmodyfikowanego konta nie powiodło się.
     */
    @PermitAll
    public Account editAccountDetails(Account account, AccountDetails newDetails) throws AppBaseException {
        AccountDetails currentDetails = account.getAccountDetails();
        currentDetails.setFirstName(newDetails.getFirstName());
        currentDetails.setLastName(newDetails.getLastName());
        currentDetails.setPhoneNumber(newDetails.getPhoneNumber());

        accountFacade.edit(account);
        return account;
    }

    /**
     * Wyszukuje konto na podstawie adresu e-mail.
     * 
     * @param email e-mail przypisany do konta.
     * @return konto o podanym e-mailu.
     * @throws AppBaseException w przypadku niepowodzenia operacji.
     */
    @PermitAll
    public Account findByEmail(String email) throws AppBaseException {
        return accountFacade.findByEmail(email);
    }

    /**
     * Zwraca listę wszystkich kont wraz z ich danymi szczegółowymi.
     *
     * @return lista wszystkich kont wraz z danymi szczegółowymi.
     */
    @RolesAllowed(Role.Admin)
    public List<Account> getAll() {
        return accountFacade.findAll();
    }

    /**
     * Zmienia hasło dla konta.
     * 
     * @param account konto, dla którego zmienione ma zostać hasło.
     * @param password nowe hasło.
     * @throws AppBaseException w przypadku niepowodzenia operacji.
     */
    public void changePassword(Account account, String password) throws AppBaseException {
        account.setPassword(BCrypt.withDefaults().hashToString(12, password.toCharArray()));
        accountFacade.edit(account);
    }

    /**
     * Sprawdza poprawność tokenu resetującego hasło, usuwa go, a następnie zmienia hasło konta.
     * 
     * @param passwordResetDto token resetujący i nowe hasło.
     * @throws AppBaseException w przypadku niepowodzenia operacji.
     */
    @PermitAll
    public void resetPassword(PasswordResetDto passwordResetDto) throws AppBaseException {
        Account account = verificationTokenService.confirmPasswordReset(UUID.fromString(passwordResetDto.getToken()));
        if (!account.getActive())
            throw AccountException.notActive(account);
        changePassword(account, passwordResetDto.getPassword());
    }

    /**
     * Aktualizuje dane ostatniego poprawnego uwierzytelnienia z konta.
     *
     * @param login           login użytkownika
     * @param lastIpAddress   adres logiczny użytkownika
     * @param currentAuth     data logowania
     * @throws AppBaseException gdy konto ma status nieaktywny.
     */
    @PermitAll
    public void updateAuthInfo(String login, String lastIpAddress, LocalDateTime currentAuth) throws AppBaseException {
        Account account = accountFacade.findByLogin(login);
        if (!account.getActive()) {
            throw AccountException.accountBlocked();
        }
        account.getAccountAuthInfo().setLastIpAddress(lastIpAddress);
        account.getAccountAuthInfo().setLastSuccessAuth(account.getAccountAuthInfo().getCurrentAuth());
        account.getAccountAuthInfo().setCurrentAuth(currentAuth);

        accountFacade.edit(account);
    }

    /**
     * Aktualizuje dane ostatniego niepoprawnego uwierzytelnienia z konta.
     * 
     * @param login login użytkownika.
     * @param lastIncorrectAuth data logowania.
     */
    @PermitAll
    public void updateAuthInfo(String login, LocalDateTime lastIncorrectAuth) throws AppBaseException {
        Account account;
        try {
            account = accountFacade.findByLogin(login);
        } catch (AccountException e) {
            return;
        }
        account.getAccountAuthInfo().setLastIncorrectAuth(lastIncorrectAuth);
        accountFacade.edit(account);
    }

    /**
     * Zmienia status aktywności dla konta o podanym loginie.
     *
     * @param login login konta, dla którego zmieniamy status aktywności.
     * @param active wartość statusu aktywności, która ma zostać ustawiona.
     * @throws AppBaseException gdy nie udało się zmienić statusu aktywności.
     */
    @RolesAllowed(Role.Admin)
    public void changeAccountActiveStatus(String login, Boolean active) throws AppBaseException {
        Account account = accountFacade.findByLogin(login);
        account.setActive(active);
        accountFacade.edit(account);
    }
}

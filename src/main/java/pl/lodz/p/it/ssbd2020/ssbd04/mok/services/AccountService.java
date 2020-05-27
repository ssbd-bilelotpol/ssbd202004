package pl.lodz.p.it.ssbd2020.ssbd04.mok.services;

import at.favre.lib.crypto.bcrypt.BCrypt;
import pl.lodz.p.it.ssbd2020.ssbd04.common.I18n;
import pl.lodz.p.it.ssbd2020.ssbd04.entities.Account;
import pl.lodz.p.it.ssbd2020.ssbd04.entities.AccountAuthInfo;
import pl.lodz.p.it.ssbd2020.ssbd04.entities.AccountDetails;
import pl.lodz.p.it.ssbd2020.ssbd04.entities.access_levels.AccountAccessLevel;
import pl.lodz.p.it.ssbd2020.ssbd04.entities.access_levels.ClientAccessLevel;
import pl.lodz.p.it.ssbd2020.ssbd04.exceptions.AccountException;
import pl.lodz.p.it.ssbd2020.ssbd04.exceptions.AppBaseException;
import pl.lodz.p.it.ssbd2020.ssbd04.interceptors.TrackingInterceptor;
import pl.lodz.p.it.ssbd2020.ssbd04.mok.dto.AccountDto;
import pl.lodz.p.it.ssbd2020.ssbd04.mok.dto.PasswordResetDto;
import pl.lodz.p.it.ssbd2020.ssbd04.mok.facades.AccountFacade;
import pl.lodz.p.it.ssbd2020.ssbd04.services.EmailService;

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
import java.util.stream.Collectors;

import static java.util.Collections.singleton;
import static pl.lodz.p.it.ssbd2020.ssbd04.security.Role.*;

/**
 * Przetwarzanie logiki biznesowej Kont.
 */

@Interceptors({TrackingInterceptor.class})
@Stateless
@TransactionAttribute(TransactionAttributeType.MANDATORY)
public class AccountService {

    @Inject
    private AccountFacade accountFacade;

    @Inject
    private VerificationTokenService verificationTokenService;

    @Inject
    private EmailService emailService;

    @Inject
    private I18n i18n;

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
        account.setAccountAuthInfo(new AccountAuthInfo(account, 0));
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

    @RolesAllowed({EditAccountAccessLevel})
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
    @RolesAllowed({EditOwnAccountDetails, EditOtherAccountDetails})
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
    @RolesAllowed(GetAllAccounts)
    public List<Account> getAll() throws AppBaseException {
        return accountFacade.findAll();
    }

    /**
     * Zmienia hasło dla konta.
     *
     * @param account  konto, dla którego zmienione ma zostać hasło.
     * @param password nowe hasło.
     * @throws AppBaseException w przypadku niepowodzenia operacji.
     */
    @RolesAllowed({ChangeOtherAccountPassword, ChangeOwnAccountPassword})
    public void changePassword(Account account, String password) throws AppBaseException {
        if (BCrypt.verifyer().verify(password.toCharArray(), account.getPassword()).verified) {
            throw AccountException.passwordIsTheSame(account);
        }
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
     * @param login         login użytkownika
     * @param lastIpAddress adres logiczny użytkownika
     * @param currentAuth   data logowania
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
        account.getAccountAuthInfo().setIncorrectAuthCount(0);

        accountFacade.edit(account);
    }

    /**
     * Aktualizuje dane ostatniego niepoprawnego uwierzytelnienia oraz blokuje konto przy trzech niepoprawnych
     * próbach uwierzytelnienia.
     *
     * @param login             login użytkownika.
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
        Integer incorrectAuthCount = account.getAccountAuthInfo().getIncorrectAuthCount();
        if (++incorrectAuthCount == 3) {
            account.setActive(false);
            sendActiveStatusChangedEmail(account, false);
        }
        account.getAccountAuthInfo().setIncorrectAuthCount(incorrectAuthCount);
        accountFacade.edit(account);
    }

    /**
     * Zmienia status aktywności dla konta o podanym loginie.
     *
     * @param login  login konta, dla którego zmieniamy status aktywności.
     * @param active wartość statusu aktywności, która ma zostać ustawiona.
     * @throws AppBaseException gdy nie udało się zmienić statusu aktywności.
     */
    @RolesAllowed(ChangeAccountActiveStatus)
    public void changeAccountActiveStatus(String login, Boolean active) throws AppBaseException {
        Account account = accountFacade.findByLogin(login);
        account.setActive(active);
        account.getAccountAuthInfo().setIncorrectAuthCount(0);
        sendActiveStatusChangedEmail(account, active);
        accountFacade.edit(account);
    }

    private void sendActiveStatusChangedEmail(Account account, boolean active) throws AppBaseException {
        String emailSubject = active ? I18n.ACCOUNT_UNBLOCKED_MAIL_TITLE : I18n.ACCOUNT_BLOCKED_MAIL_TITLE;
        String emailSender = active ? I18n.ACCOUNT_UNBLOCKED_MAIL_SENDER : I18n.ACCOUNT_BLOCKED_MAIL_SENDER;
        String message = active ? I18n.ACCOUNT_UNBLOCKED_MAIL_CONTENT : I18n.ACCOUNT_BLOCKED_MAIL_CONTENT;
        emailService.sendTransactionalEmail(account.getAccountDetails().getEmail(),
                i18n.getMessage(emailSender),
                i18n.getMessage(emailSubject),
                i18n.getMessage(message) + i18n.getMessage(I18n.MAIL_FOOTER));
    }

    /**
     * Zwraca listę wszystkich kont wraz z ich danymi szczegółowymi, dla których imię i nazwisko jest zgodne z podaną frazą.
     *
     * @param name fraza, której poszukujemy. Jeśli name jest pustym ciągiem znaków lub null, to metoda zwraca wszystkie konta.
     * @return lista konta wraz z danymi szczegółowymi.
     * @throws AppBaseException gdy nie udało się znaleźć żadnego konta zgodnego z podaną frazą.
     */
    @RolesAllowed(FindAccountsByName)
    public List<AccountDto> findByName(String name) throws AppBaseException {
        return accountFacade.findByName(name).stream().map(AccountDto::new).collect(Collectors.toList());
    }
}

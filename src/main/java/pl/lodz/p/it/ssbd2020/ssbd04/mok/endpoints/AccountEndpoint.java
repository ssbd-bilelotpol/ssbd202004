package pl.lodz.p.it.ssbd2020.ssbd04.mok.endpoints;

import pl.lodz.p.it.ssbd2020.ssbd04.exceptions.AccountException;
import pl.lodz.p.it.ssbd2020.ssbd04.exceptions.AppBaseException;
import pl.lodz.p.it.ssbd2020.ssbd04.mok.dto.AccountAccessLevelDto;
import pl.lodz.p.it.ssbd2020.ssbd04.mok.dto.PasswordResetDto;
import pl.lodz.p.it.ssbd2020.ssbd04.mok.entities.Account;
import pl.lodz.p.it.ssbd2020.ssbd04.mok.entities.AccountDetails;
import pl.lodz.p.it.ssbd2020.ssbd04.mok.services.AccountService;
import pl.lodz.p.it.ssbd2020.ssbd04.mok.services.VerificationTokenService;
import pl.lodz.p.it.ssbd2020.ssbd04.mok.dto.AccountDto;
import pl.lodz.p.it.ssbd2020.ssbd04.mok.dto.AccountEditDto;
import pl.lodz.p.it.ssbd2020.ssbd04.security.AuthContext;
import pl.lodz.p.it.ssbd2020.ssbd04.security.Role;
import pl.lodz.p.it.ssbd2020.ssbd04.utils.AbstractEndpoint;

import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;
import java.util.UUID;

/**
 * Wykonuje konwersję klas DTO na model biznesowy
 * i jest granicą transakcji aplikacyjnej dla hierarchii klas Account i AccountAccessLevel
 */
@Stateless
@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
@PermitAll
public class AccountEndpoint extends AbstractEndpoint {

    @Inject
    private AccountService accountService;

    @Inject
    private VerificationTokenService tokenService;

    @Inject
    private AuthContext auth;

    public void register(Account account, AccountDetails accountDetails) throws AppBaseException {
        accountService.register(account, accountDetails);
    }

    public void confirm(UUID fromString) throws AppBaseException {
        accountService.confirm(fromString);
    }

    @RolesAllowed(Role.Admin)
    public AccountAccessLevelDto editAccountAccessLevel(String login, AccountAccessLevelDto accountAccessLevelDto) throws AppBaseException {
        Account account = accountService.findByLogin(login);
        AccountAccessLevelDto currentAccountAccessLevelDto = new AccountAccessLevelDto(account);
        if (!verifyEtag(currentAccountAccessLevelDto)) {
            throw AppBaseException.optimisticLock();
        }

        accountService.editAccountAccessLevel(account, accountAccessLevelDto.toAccountAccessLevelSet());
        return new AccountAccessLevelDto(account);
    }

    @RolesAllowed(Role.Admin)
    public AccountAccessLevelDto getAccessLevels(String login) throws AppBaseException {
        return new AccountAccessLevelDto(accountService.findByLogin(login));
    }

    /**
     * Zwraca dane konta inicjującego żądanie
     *
     * @return konto inicjujące żądanie
     * @throws AppBaseException gdy nie udało się pobrać danych konta
     */
    @RolesAllowed(Role.Client)
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
    @RolesAllowed(Role.Admin)
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
    @RolesAllowed(Role.Client)
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
    @RolesAllowed(Role.Admin)
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
     * Użytkownik musi być aktywny, a jego rejestracja potwierdzona.
     * @param email email użytkownika
     * @throws AppBaseException w przypadku niepowodzenia operacji
     */
    public void sendResetPasswordToken(String email) throws AppBaseException {
        Account account = accountService.findByEmail(email);
        if(!account.getActive())
            throw AccountException.notActive(account);
        if(!account.getConfirm())
            throw AccountException.notConfirmed(account);
        tokenService.sendResetPasswordToken(account);
    }

    /**
     * Resetuje hasło za pomocą tokenu resetującego.
     * @param passwordResetDto token resetujący oraz nowe hasło
     * @throws AppBaseException w przypadku niepowodzenia operacji
     */
    public void resetPassword(PasswordResetDto passwordResetDto) throws AppBaseException {
        accountService.resetPassword(passwordResetDto);
    }
}

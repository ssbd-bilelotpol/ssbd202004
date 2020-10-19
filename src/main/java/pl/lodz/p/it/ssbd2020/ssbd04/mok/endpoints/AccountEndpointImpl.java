package pl.lodz.p.it.ssbd2020.ssbd04.mok.endpoints;

import at.favre.lib.crypto.bcrypt.BCrypt;
import pl.lodz.p.it.ssbd2020.ssbd04.common.AbstractEndpoint;
import pl.lodz.p.it.ssbd2020.ssbd04.common.I18n;
import pl.lodz.p.it.ssbd2020.ssbd04.entities.Account;
import pl.lodz.p.it.ssbd2020.ssbd04.entities.AccountDetails;
import pl.lodz.p.it.ssbd2020.ssbd04.exceptions.AccountException;
import pl.lodz.p.it.ssbd2020.ssbd04.exceptions.AppBaseException;
import pl.lodz.p.it.ssbd2020.ssbd04.interceptors.TrackingInterceptor;
import pl.lodz.p.it.ssbd2020.ssbd04.mok.dto.*;
import pl.lodz.p.it.ssbd2020.ssbd04.mok.services.AccountService;
import pl.lodz.p.it.ssbd2020.ssbd04.mok.services.VerificationTokenService;
import pl.lodz.p.it.ssbd2020.ssbd04.security.AuthContext;
import pl.lodz.p.it.ssbd2020.ssbd04.services.EmailService;

import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;
import javax.ejb.Stateful;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;
import javax.inject.Named;
import javax.interceptor.Interceptors;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import static pl.lodz.p.it.ssbd2020.ssbd04.common.I18n.*;
import static pl.lodz.p.it.ssbd2020.ssbd04.security.Role.*;

/**
 * Wykonuje konwersję klas DTO na model biznesowy
 * i jest granicą transakcji aplikacyjnej dla hierarchii klas Account i AccountAccessLevel.
 */
@Interceptors({TrackingInterceptor.class})
@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
@Stateful
public class AccountEndpointImpl extends AbstractEndpoint implements AccountEndpoint {

    @Named("AccountServiceMOK")
    @Inject
    private AccountService accountService;

    @Inject
    private VerificationTokenService tokenService;

    @Inject
    private EmailService emailService;

    @Inject
    private AuthContext auth;

    @Inject
    private I18n i18n;

    @Override
    @PermitAll
    public void register(AccountRegisterDto accountRegisterDto) throws AppBaseException {
        Account account = new Account();
        account.setLogin(accountRegisterDto.getLogin());
        account.setPassword(accountRegisterDto.getPassword());

        AccountDetails accountDetails = new AccountDetails();
        accountDetails.setEmail(accountRegisterDto.getEmail());
        accountDetails.setFirstName(accountRegisterDto.getFirstName());
        accountDetails.setLastName(accountRegisterDto.getLastName());
        accountDetails.setPhoneNumber(accountRegisterDto.getPhoneNumber());

        accountService.register(account, accountDetails);
    }

    @Override
    @PermitAll
    public void confirm(UUID fromString) throws AppBaseException {
        accountService.confirm(fromString);
    }

    @Override
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

    @Override
    @RolesAllowed(GetAccessLevels)
    public AccountAccessLevelDto getAccessLevels(String login) throws AppBaseException {
        return new AccountAccessLevelDto(accountService.findByLogin(login));
    }

    @Override
    @RolesAllowed(RetrieveOwnAccountDetails)
    public AccountDto retrieveOwnAccountDetails() throws AppBaseException {
        return new AccountDto(auth.currentUser());
    }

    @Override
    @RolesAllowed(RetrieveOtherAccountDetails)
    public AccountDto retrieveOtherAccountDetails(String login) throws AppBaseException {
        Account account = accountService.findByLogin(login);
        return new AccountDto(account);
    }

    @Override
    @RolesAllowed(EditOwnAccountDetails)
    public AccountDto editOwnAccountDetails(AccountEditDto accountEditDto) throws AppBaseException {
        Account account = auth.currentUser();
        AccountDto currentAccountDto = new AccountDto(account);
        if (!verifyEtag(currentAccountDto)) {
            throw AppBaseException.optimisticLock();
        }

        return editAccountDetails(account, accountEditDto);
    }

    @Override
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

    @Override
    @PermitAll
    public void sendResetPasswordToken(String email) throws AppBaseException {
        Account account = accountService.findByEmail(email);
        if (!account.getActive())
            throw AccountException.notActive(account);
        if (!account.getConfirm())
            throw AccountException.notConfirmed(account);
        tokenService.sendResetPasswordToken(account);
    }

    @Override
    @PermitAll
    public void resetPassword(PasswordResetDto passwordResetDto) throws AppBaseException {
        accountService.resetPassword(passwordResetDto);
    }

    @Override
    @PermitAll
    public void updateAuthInfo(String login, String lastIpAddress, LocalDateTime currentAuth) throws AppBaseException {
        accountService.updateAuthInfo(login, lastIpAddress, currentAuth);
    }

    @Override
    @PermitAll
    public void updateAuthInfo(String username, LocalDateTime lastIncorrectAuth) throws AppBaseException {
        accountService.updateAuthInfo(username, lastIncorrectAuth);
    }

    @Override
    @RolesAllowed(GetAllAccountsAuthInfo)
    public List<AccountAuthInfoDto> getAllAccountsAuthInfo() throws AppBaseException {
        return accountService.getAll().stream()
                .map(a -> new AccountAuthInfoDto(a.getLogin(),
                        a.getAccountAuthInfo().getCurrentAuth(),
                        a.getAccountAuthInfo().getLastIpAddress())).collect(Collectors.toList());
    }

    @Override
    @RolesAllowed(GetAccountAuthInfo)
    public AccountAuthInfoDto getAccountAuthInfo() throws AppBaseException {
        Account account = auth.currentUser();
        return new AccountAuthInfoDto(account.getAccountAuthInfo().getLastSuccessAuth(),
                account.getAccountAuthInfo().getLastIncorrectAuth());
    }

    @Override
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

    @Override
    @RolesAllowed(ChangeOtherAccountPassword)
    public void changeOtherAccountPassword(String login, String password) throws AppBaseException {
        Account account = accountService.findByLogin(login);
        AccountDto accountDto = new AccountDto(account);

        if (!verifyEtag(accountDto)) throw AppBaseException.optimisticLock();

        accountService.changePassword(account, password);
    }

    @Override
    @RolesAllowed(FindAccountsByName)
    public List<AccountDto> findByName(String name) throws AppBaseException {
        return accountService.findByName(name);
    }

    @Override
    @RolesAllowed(ChangeAccountActiveStatus)
    public void changeAccountActiveStatus(String login, Boolean active) throws AppBaseException {
        accountService.changeAccountActiveStatus(login, active);
    }

    @Override
    @PermitAll
    public void notifyAboutAdminLogin(String login, String remoteIP) throws AppBaseException {
        emailService.sendEmail(this.retrieveOtherAccountDetails(login).getEmail(),
                i18n.getMessage(ACCOUNT_ADMIN_LOGIN_SENDER),
                i18n.getMessage(ACCOUNT_ADMIN_LOGIN_TITLE),
                i18n.getMessage(ACCOUNT_ADMIN_LOGIN_CONTENT) + remoteIP);
    }

}

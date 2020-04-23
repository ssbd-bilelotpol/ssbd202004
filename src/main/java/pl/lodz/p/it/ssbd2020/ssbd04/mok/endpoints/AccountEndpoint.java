package pl.lodz.p.it.ssbd2020.ssbd04.mok.endpoints;

import pl.lodz.p.it.ssbd2020.ssbd04.exceptions.AppBaseException;
import pl.lodz.p.it.ssbd2020.ssbd04.mok.dto.AccountAccessLevelDto;
import pl.lodz.p.it.ssbd2020.ssbd04.mok.entities.Account;
import pl.lodz.p.it.ssbd2020.ssbd04.mok.entities.AccountDetails;
import pl.lodz.p.it.ssbd2020.ssbd04.mok.services.AccountService;
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
        if (!verifyEtag(currentAccountAccessLevelDto)) return null;
        accountService.editAccountAccessLevel(account, accountAccessLevelDto.toAccountAccessLevelSet());
        return new AccountAccessLevelDto(account);
    }

    @RolesAllowed(Role.Admin)
    public AccountAccessLevelDto getAccessLevels(String login) throws AppBaseException {
        AccountAccessLevelDto accountAccessLevelDto = new AccountAccessLevelDto(accountService.findByLogin(login));
        if (verifyEtag(accountAccessLevelDto)) return null;
        return accountAccessLevelDto;
    }
}

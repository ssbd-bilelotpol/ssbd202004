package pl.lodz.p.it.ssbd2020.ssbd04.mok.services;

import at.favre.lib.crypto.bcrypt.BCrypt;
import pl.lodz.p.it.ssbd2020.ssbd04.exceptions.AppBaseException;
import pl.lodz.p.it.ssbd2020.ssbd04.exceptions.VerificationTokenException;
import pl.lodz.p.it.ssbd2020.ssbd04.mok.entities.Account;
import pl.lodz.p.it.ssbd2020.ssbd04.mok.entities.AccountDetails;
import pl.lodz.p.it.ssbd2020.ssbd04.mok.entities.access_levels.ClientAccessLevel;
import pl.lodz.p.it.ssbd2020.ssbd04.mok.entities.verification_tokens.RegisterToken;
import pl.lodz.p.it.ssbd2020.ssbd04.mok.entities.verification_tokens.VerificationToken;
import pl.lodz.p.it.ssbd2020.ssbd04.mok.facades.AccountFacade;
import pl.lodz.p.it.ssbd2020.ssbd04.mok.facades.VerificationTokenFacade;
import pl.lodz.p.it.ssbd2020.ssbd04.security.Role;

import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import java.time.LocalDateTime;
import java.util.UUID;

import static java.util.Collections.singleton;

/**
 * Przetwarzanie logiki biznesowej Kont.
 */
@Stateless
@TransactionAttribute(TransactionAttributeType.MANDATORY)
@RolesAllowed(Role.Admin)
public class AccountService {

    @Inject
    private AccountFacade accountFacade;

    @Inject
    private VerificationTokenService verificationTokenService;

    /**
     * Rejestruje konto, przypisując do niego dane personalne i wysyła żeton potwierdzający na email.
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
        accountFacade.create(account);

        verificationTokenService.sendRegisterToken(account);
    }

    /**
     * Potwierdza konto na podstawie otrzymanego żetonu.
     * @param tokenId
     * @throws AppBaseException
     */
    @PermitAll
    public void confirm(UUID tokenId) throws AppBaseException {
        Account account = verificationTokenService.confirm(tokenId);
        account.setConfirm(true);
        accountFacade.edit(account);
    }
}
package pl.lodz.p.it.ssbd2020.ssbd04.security;

import pl.lodz.p.it.ssbd2020.ssbd04.entities.Account;
import pl.lodz.p.it.ssbd2020.ssbd04.exceptions.AppBaseException;
import pl.lodz.p.it.ssbd2020.ssbd04.mok.services.AccountService;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.security.enterprise.SecurityContext;

@Stateless
public class AuthContext {

    @Inject
    private AccountService accountService;

    @Inject
    private SecurityContext securityContext;

    public Account currentUser() throws AppBaseException {
        return accountService.findByLogin(securityContext.getCallerPrincipal().getName());
    }

}

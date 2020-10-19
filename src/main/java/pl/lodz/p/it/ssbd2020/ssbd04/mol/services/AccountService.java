package pl.lodz.p.it.ssbd2020.ssbd04.mol.services;

import pl.lodz.p.it.ssbd2020.ssbd04.entities.Account;
import pl.lodz.p.it.ssbd2020.ssbd04.exceptions.AppBaseException;
import pl.lodz.p.it.ssbd2020.ssbd04.interceptors.TrackingInterceptor;
import pl.lodz.p.it.ssbd2020.ssbd04.mol.facades.AccountFacade;
import pl.lodz.p.it.ssbd2020.ssbd04.security.Role;

import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;
import javax.inject.Named;
import javax.interceptor.Interceptors;
import javax.security.enterprise.SecurityContext;
import java.util.List;

/**
 * Przetwarzanie logiki biznesowej Kont
 */
@Interceptors({TrackingInterceptor.class})
@Stateless(name = "AccountServiceMOL")
@TransactionAttribute(TransactionAttributeType.MANDATORY)
public class AccountService {

    @Named("AccountFacadeMOL")
    @Inject
    private AccountFacade accountFacade;

    @Inject
    private SecurityContext securityContext;

    /**
     * Zwraca zautoryzowanego użytkownika wykonującego żądanie
     *
     * @return konto użytkownika
     */
    @PermitAll
    public Account getCurrentUser() {
        if (securityContext.getCallerPrincipal() == null) {
            return null;
        }

        try {
            return accountFacade.findByLogin(securityContext.getCallerPrincipal().getName());
        } catch (AppBaseException e) {
            return null;
        }
    }

    /**
     * Zwraca konta, które mają bilety na wybrany lot.
     *
     * @param flightCode kod lotu
     * @return lista kont
     * @throws AppBaseException gdy operacja nie powiedzie się
     */
    @RolesAllowed({Role.UpdateFlight, Role.CancelFlight})
    public List<Account> getAccountsByTicketsOwnedForFlight(String flightCode) throws AppBaseException {
        return accountFacade.getAccountsByTicketsOwnedForFlight(flightCode);
    }
}

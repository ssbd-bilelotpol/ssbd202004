package pl.lodz.p.it.ssbd2020.ssbd04.mob.services;

import pl.lodz.p.it.ssbd2020.ssbd04.entities.Account;
import pl.lodz.p.it.ssbd2020.ssbd04.exceptions.AppBaseException;
import pl.lodz.p.it.ssbd2020.ssbd04.interceptors.TrackingInterceptor;
import pl.lodz.p.it.ssbd2020.ssbd04.mob.facades.AccountFacade;

import javax.annotation.security.PermitAll;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;
import javax.inject.Named;
import javax.interceptor.Interceptors;
import javax.security.enterprise.SecurityContext;

/**
 * Przetwarzanie logiki biznesowej Kont.
 */
@Interceptors({TrackingInterceptor.class})
@Stateless(name = "AccountServiceMOB")
@TransactionAttribute(TransactionAttributeType.MANDATORY)
public class AccountService {

    @Named("AccountFacadeMOB")
    @Inject()
    private AccountFacade accountFacade;

    @Inject
    private SecurityContext securityContext;

    /**
     * Wyszukuje konto na podstawie loginu
     *
     * @param login szukany login
     * @return znalezione konto
     * @throws AppBaseException gdy konto nie zostało znalezione, lub wystąpił problem z bazą danych.
     */
    @PermitAll
    public Account findByLogin(String login) throws AppBaseException {
        return accountFacade.findByLogin(login);
    }

    /**
     * Zwraca konto zalogowanego użytkownika wykonujacego ządanie
     *
     * @return konto zalogowanego użytkownika
     */
    @PermitAll
    public Account getCurrentUser() {
        if (securityContext.getCallerPrincipal() == null) {
            return null;
        }

        try {
            return findByLogin(securityContext.getCallerPrincipal().getName());
        } catch (AppBaseException e) {
            return null;
        }
    }

    /**
     * Sprawdza czy użytkownik wykonujący żądanie posiada daną rolę
     *
     * @param name nazwa roli
     * @return czy użytkownik ma role
     */
    @PermitAll
    public boolean hasRole(String name) {
        return securityContext.isCallerInRole(name);
    }

}

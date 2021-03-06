package pl.lodz.p.it.ssbd2020.ssbd04.mol.facades;

import pl.lodz.p.it.ssbd2020.ssbd04.common.AbstractFacade;
import pl.lodz.p.it.ssbd2020.ssbd04.entities.Account;
import pl.lodz.p.it.ssbd2020.ssbd04.exceptions.AccountException;
import pl.lodz.p.it.ssbd2020.ssbd04.exceptions.AppBaseException;
import pl.lodz.p.it.ssbd2020.ssbd04.interceptors.TrackingInterceptor;
import pl.lodz.p.it.ssbd2020.ssbd04.security.Role;

import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.interceptor.Interceptors;
import javax.persistence.*;
import java.util.List;


/**
 * Klasa definiująca operacje wykonywane na encjach klasy Account
 * przez zarządcę encji w kontekście trwałości.
 */
@Interceptors({TrackingInterceptor.class})
@Stateless
@TransactionAttribute(TransactionAttributeType.MANDATORY)
public class AccountFacade extends AbstractFacade<Account> {

    @PersistenceContext(unitName = "ssbd04molPU")
    private EntityManager em;

    public AccountFacade() {
        super(Account.class);
    }

    /**
     * Wyszukuje konto na podstawie loginu
     *
     * @param login podany login
     * @return znalezione konto
     * @throws AppBaseException gdy konto nie zostało znalezione, lub wystąpił problem z bazą danych.
     */
    @PermitAll
    public Account findByLogin(String login) throws AppBaseException {
        try {
            TypedQuery<Account> accountTypedQuery = em.createNamedQuery("Account.findByLogin", Account.class);
            accountTypedQuery.setFlushMode(FlushModeType.COMMIT);
            accountTypedQuery.setParameter("login", login);

            return accountTypedQuery.getSingleResult();
        } catch (NoResultException e) {
            throw AccountException.noExists(e);
        } catch (PersistenceException e) {
            throw AppBaseException.databaseOperation(e);
        }
    }

    /**
     * Wyszukuje konta, które mają przypisane bilety na dany lot.
     *
     * @param flightCode kod lotu
     * @return lista kont
     * @throws AppBaseException gdy wystapi problem z bazą danych.
     */
    @RolesAllowed({Role.UpdateFlight, Role.CancelFlight})
    public List<Account> getAccountsByTicketsOwnedForFlight(String flightCode) throws AppBaseException {
        try {
            TypedQuery<Account> accountTypedQuery = em.createNamedQuery("Flight.findAccountsByTicketsForFlight", Account.class);
            accountTypedQuery.setParameter("code", flightCode);
            return accountTypedQuery.getResultList();
        } catch (PersistenceException e) {
            throw AppBaseException.databaseOperation(e);
        }
    }

    @Override
    protected EntityManager getEntityManager() {
        return this.em;
    }

}

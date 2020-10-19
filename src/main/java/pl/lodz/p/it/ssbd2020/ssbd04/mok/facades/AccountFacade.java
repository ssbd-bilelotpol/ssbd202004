package pl.lodz.p.it.ssbd2020.ssbd04.mok.facades;

import org.hibernate.exception.ConstraintViolationException;
import pl.lodz.p.it.ssbd2020.ssbd04.common.AbstractFacade;
import pl.lodz.p.it.ssbd2020.ssbd04.entities.Account;
import pl.lodz.p.it.ssbd2020.ssbd04.entities.AccountDetails;
import pl.lodz.p.it.ssbd2020.ssbd04.entities.access_levels.AccountAccessLevel;
import pl.lodz.p.it.ssbd2020.ssbd04.exceptions.AccountAccessLevelException;
import pl.lodz.p.it.ssbd2020.ssbd04.exceptions.AccountException;
import pl.lodz.p.it.ssbd2020.ssbd04.exceptions.AppBaseException;
import pl.lodz.p.it.ssbd2020.ssbd04.interceptors.TrackingInterceptor;

import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Named;
import javax.interceptor.Interceptors;
import javax.persistence.*;
import java.util.List;

import static pl.lodz.p.it.ssbd2020.ssbd04.security.Role.FindAccountsByName;

@Interceptors({TrackingInterceptor.class})
@Stateless(name = "AccountFacadeMOK")
@Named("AccountFacadeMOK")
@TransactionAttribute(TransactionAttributeType.MANDATORY)
public class AccountFacade extends AbstractFacade<Account> {

    @PersistenceContext(unitName = "ssbd04mokPU")
    private EntityManager em;

    public AccountFacade() {
        super(Account.class);
    }

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    /**
     * Zapisuje konto w bazie danych i sprawdza, czy nie naruszono unikalności loginu i hasła.
     *
     * @param account obiekt encji konta
     * @throws AppBaseException gdy login lub email jest zajęty, bądź wystąpił problem z bazą danych.
     */
    @Override
    @PermitAll
    public void create(Account account) throws AppBaseException {
        try {
            super.create(account);
        } catch (ConstraintViolationException e) {
            if (e.getCause().getMessage().contains(Account.CONSTRAINT_LOGIN)) {
                throw AccountException.loginExists(e, account);
            } else if (e.getCause().getMessage().contains(AccountDetails.CONSTRAINT_EMAIL)) {
                throw AccountException.emailExists(e, account);
            }
            throw AppBaseException.databaseOperation(e);
        }
    }

    @Override
    @PermitAll
    public void remove(Account entity) throws AppBaseException {
        super.remove(entity);
    }

    /**
     * Zwraca listę wszystkich kont wraz z ich danymi szczegółowymi, dla których imię i nazwisko jest zgodne z podaną frazą.
     *
     * @param name fraza, której poszukujemy. Jeśli name jest pustym ciągiem znaków lub null, to metoda zwraca wszystkie konta.
     * @return lista konta wraz z danymi szczegółowymi.
     * @throws AppBaseException gdy nie udało się znaleźć żadnego konta zgodnego z podaną frazą.
     */
    @RolesAllowed(FindAccountsByName)
    public List<Account> findByName(String name) throws AppBaseException {
        try {
            TypedQuery<Account> accountTypedQuery = em.createNamedQuery("Account.findByName", Account.class);
            accountTypedQuery.setParameter("name", name == null ? "" : name);
            return accountTypedQuery.getResultList();
        } catch (NoResultException e) {
            throw AccountException.noExists(e);
        } catch (PersistenceException e) {
            throw AppBaseException.databaseOperation(e);
        }
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
     * Wyszukuje konto na podstawie emailu
     *
     * @param email podany email
     * @return znalezione konto
     * @throws AppBaseException gdy konto nie zostało znalezione, lub wystąpił problem z bazą danych.
     */
    @PermitAll
    public Account findByEmail(String email) throws AppBaseException {
        try {
            TypedQuery<Account> accountTypedQuery = em.createNamedQuery("Account.findByEmail", Account.class);
            accountTypedQuery.setParameter("email", email);
            return accountTypedQuery.getSingleResult();
        } catch (NoResultException e) {
            throw AccountException.noExists(e);
        } catch (PersistenceException e) {
            throw AppBaseException.databaseOperation(e);
        }
    }

    @PermitAll
    @Override
    public List<Account> findAll() throws AppBaseException {
        return super.findAll();
    }

    @Override
    @PermitAll
    public void edit(Account entity) throws AppBaseException {
        try {
            super.edit(entity);
        } catch (ConstraintViolationException e) {
            if (e.getCause().getMessage().contains(Account.CONSTRAINT_LOGIN)) {
                throw AccountException.loginExists(e, entity);
            } else if (e.getCause().getMessage().contains(AccountDetails.CONSTRAINT_EMAIL)) {
                throw AccountException.emailExists(e, entity);
            } else if (e.getCause().getMessage().contains(AccountAccessLevel.CONSTRAINT_UNIQUE_ACCOUNT_ACCESS_LEVEL)) {
                throw AccountAccessLevelException.alreadyAssigned(e);
            }
            throw AppBaseException.databaseOperation(e);
        }
    }
}

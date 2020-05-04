package pl.lodz.p.it.ssbd2020.ssbd04.mok.facades;

import org.hibernate.exception.ConstraintViolationException;
import pl.lodz.p.it.ssbd2020.ssbd04.common.AbstractFacade;
import pl.lodz.p.it.ssbd2020.ssbd04.entities.Account;
import pl.lodz.p.it.ssbd2020.ssbd04.entities.AccountDetails;
import pl.lodz.p.it.ssbd2020.ssbd04.entities.access_levels.AccountAccessLevel;
import pl.lodz.p.it.ssbd2020.ssbd04.exceptions.AccountAccessLevelException;
import pl.lodz.p.it.ssbd2020.ssbd04.exceptions.AccountException;
import pl.lodz.p.it.ssbd2020.ssbd04.exceptions.AppBaseException;
import pl.lodz.p.it.ssbd2020.ssbd04.security.Role;

import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.*;


@Stateless
@TransactionAttribute(TransactionAttributeType.MANDATORY)
@RolesAllowed(Role.Admin)
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
     * @param account
     * @throws AppBaseException
     */
    @Override
    @PermitAll
    public void create(Account account) throws AppBaseException {
        try {
            super.create(account);
        } catch (ConstraintViolationException e) {
            if (e.getConstraintName().equals(Account.CONSTRAINT_LOGIN)) {
                throw AccountException.loginExists(e, account);
            } else if (e.getConstraintName().equals(AccountDetails.CONSTRAINT_EMAIL)) {
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

    @PermitAll
    public Account findByLogin(String login) throws AppBaseException {
        try {
            TypedQuery<Account> accountTypedQuery = em.createNamedQuery("Account.findByLogin", Account.class);
            accountTypedQuery.setParameter("login", login);
            return accountTypedQuery.getSingleResult();
        } catch (NoResultException e) {
            throw AccountException.noExists(e);
        } catch (PersistenceException e) {
            throw AppBaseException.databaseOperation(e);
        }
    }

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

    @Override
    @PermitAll
    public void edit(Account entity) throws AppBaseException {
        try {
            super.edit(entity);
        } catch (ConstraintViolationException e) {
            switch (e.getConstraintName()) {
                case Account.CONSTRAINT_LOGIN:
                    throw AccountException.loginExists(e, entity);
                case AccountDetails.CONSTRAINT_EMAIL:
                    throw AccountException.emailExists(e, entity);
                case AccountAccessLevel.CONSTRAINT_UNIQUE_ACCOUNT_ACCESS_LEVEL:
                    throw AccountAccessLevelException.alreadyAssigned(e);
            }
            throw AppBaseException.databaseOperation(e);
        }
    }
}

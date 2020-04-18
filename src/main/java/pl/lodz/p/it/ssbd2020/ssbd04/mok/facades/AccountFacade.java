package pl.lodz.p.it.ssbd2020.ssbd04.mok.facades;

import org.hibernate.exception.ConstraintViolationException;
import pl.lodz.p.it.ssbd2020.ssbd04.exceptions.AccountException;
import pl.lodz.p.it.ssbd2020.ssbd04.exceptions.AppBaseException;
import pl.lodz.p.it.ssbd2020.ssbd04.mok.entities.Account;
import pl.lodz.p.it.ssbd2020.ssbd04.mok.entities.AccountDetails;
import pl.lodz.p.it.ssbd2020.ssbd04.security.Role;
import pl.lodz.p.it.ssbd2020.ssbd04.utils.AbstractFacade;

import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;


@Stateless
@TransactionAttribute(TransactionAttributeType.MANDATORY)
@RolesAllowed(Role.Admin)
public class AccountFacade extends AbstractFacade<Account> {
    @PersistenceContext(unitName = "ssbd04mokPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public AccountFacade() {
        super(Account.class);
    }

    /**
     * Zapisuje konto w bazie danych i sprawdza, czy nie naruszono unikalności loginu i hasła.
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
            } else if (e.getConstraintName().equals(AccountDetails.CONSTRAINT_EMAIL)){
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
}

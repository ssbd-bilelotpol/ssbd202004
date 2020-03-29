package pl.lodz.p.it.ssbd2020.ssbd04.security.persistence;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;

@Stateless
public class AuthFacade {
    @PersistenceContext(unitName = "ssbd04authPU")
    private EntityManager em;

    public Account findByLogin(String login) {
        try {
            return em.createQuery(
                    "SELECT acc FROM Account acc WHERE acc.login = :login and acc.active=true",
                    Account.class)
                    .setParameter("login", login)
                    .getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }
}

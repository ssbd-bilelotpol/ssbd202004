package pl.lodz.p.it.ssbd2020.ssbd04.mok.facades;

import pl.lodz.p.it.ssbd2020.ssbd04.utils.AbstractFacade;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import pl.lodz.p.it.ssbd2020.ssbd04.mok.entities.access_levels.AccountAccessLevel;


@Stateless
public class AccountAccessLevelFacade extends AbstractFacade<AccountAccessLevel> {

    @PersistenceContext(unitName = "ssbd04mokPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public AccountAccessLevelFacade() {
        super(AccountAccessLevel.class);
    }
    
}

package pl.lodz.p.it.ssbd2020.ssbd04.mok.facades;

import pl.lodz.p.it.ssbd2020.ssbd04.utils.AbstractFacade;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import pl.lodz.p.it.ssbd2020.ssbd04.mok.entities.AccountDetails;


@Stateless
public class AccountDetailsFacade extends AbstractFacade<AccountDetails> {

    @PersistenceContext(unitName = "ssbd04mokPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public AccountDetailsFacade() {
        super(AccountDetails.class);
    }
    
}

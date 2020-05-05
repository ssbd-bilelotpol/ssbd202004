package pl.lodz.p.it.ssbd2020.ssbd04.mob.facades;

import pl.lodz.p.it.ssbd2020.ssbd04.common.AbstractFacade;
import pl.lodz.p.it.ssbd2020.ssbd04.entities.Benefit;
import pl.lodz.p.it.ssbd2020.ssbd04.interceptors.TrackingInterceptor;

import javax.ejb.Stateless;
import javax.interceptor.Interceptors;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;


@Interceptors({TrackingInterceptor.class})
@Stateless
public class BenefitFacade extends AbstractFacade<Benefit> {

    @PersistenceContext(unitName = "ssbd04mobPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public BenefitFacade() {
        super(Benefit.class);
    }

}

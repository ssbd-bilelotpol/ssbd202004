package pl.lodz.p.it.ssbd2020.ssbd04.mol.facades;

import pl.lodz.p.it.ssbd2020.ssbd04.common.AbstractFacade;
import pl.lodz.p.it.ssbd2020.ssbd04.entities.SeatClass;
import pl.lodz.p.it.ssbd2020.ssbd04.exceptions.AppBaseException;
import pl.lodz.p.it.ssbd2020.ssbd04.interceptors.TrackingInterceptor;
import pl.lodz.p.it.ssbd2020.ssbd04.security.Role;

import javax.annotation.security.DenyAll;
import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.interceptor.Interceptors;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;


@Interceptors({TrackingInterceptor.class})
@Stateless
@TransactionAttribute(TransactionAttributeType.MANDATORY)
public class SeatClassFacade extends AbstractFacade<SeatClass> {

    @PersistenceContext(unitName = "ssbd04molPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public SeatClassFacade() {
        super(SeatClass.class);
    }

    @Override
    @DenyAll
    public SeatClass find(Object id) {
        return super.find(id);
    }

    @PermitAll
    public SeatClass findByName(String name) throws AppBaseException {
        // throws SeatClassNotFound
        throw new UnsupportedOperationException();
    }

    @Override
    @PermitAll
    public List<SeatClass> findAll() throws AppBaseException {
        throw new UnsupportedOperationException();
    }

    @Override
    @RolesAllowed(Role.CreateSeatClass)
    public void create(SeatClass entity) throws AppBaseException {
        // throws: BenefitAlreadyExists
        // throws: SeatClassNameTaken
        throw new UnsupportedOperationException();
    }

    @Override
    @RolesAllowed(Role.DeleteSeatClass)
    public void remove(SeatClass entity) throws AppBaseException {
        // throws: SeatClassInUse
        throw new UnsupportedOperationException();
    }

    @Override
    @RolesAllowed(Role.UpdateSeatClass)
    public void edit(SeatClass entity) throws AppBaseException {
        // throws: SeatClassNameTaken
        // throws: BenefitAlreadyExists
        throw new UnsupportedOperationException();
    }
}

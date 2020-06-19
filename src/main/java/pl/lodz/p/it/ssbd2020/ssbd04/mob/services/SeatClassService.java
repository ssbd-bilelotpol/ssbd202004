package pl.lodz.p.it.ssbd2020.ssbd04.mob.services;

import pl.lodz.p.it.ssbd2020.ssbd04.entities.SeatClass;
import pl.lodz.p.it.ssbd2020.ssbd04.exceptions.AppBaseException;
import pl.lodz.p.it.ssbd2020.ssbd04.interceptors.TrackingInterceptor;
import pl.lodz.p.it.ssbd2020.ssbd04.mob.facades.SeatClassFacade;
import pl.lodz.p.it.ssbd2020.ssbd04.security.Role;

import javax.annotation.security.RolesAllowed;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;
import javax.interceptor.Interceptors;

/**
 * Przetwarzanie logiki biznesowej klas siedzeń.
 */
@Interceptors({TrackingInterceptor.class})
@Stateless
@TransactionAttribute(TransactionAttributeType.MANDATORY)
public class SeatClassService {

    @Inject
    private SeatClassFacade seatClassFacade;

    /**
     * Znajduje klasę miejsc na podstawie jej nazwy.
     *
     * @param id klasy miejsc
     * @return dane klasy miejsc.
     * @throws AppBaseException gdy operacja nie powiedzie się, bądź klasa miejsc nie istnieje.
     */
    @RolesAllowed(Role.FindSeatClassByName)
    public SeatClass find(Long id) throws AppBaseException {
        return seatClassFacade.find(id);
    }

}

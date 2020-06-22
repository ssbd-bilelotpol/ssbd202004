package pl.lodz.p.it.ssbd2020.ssbd04.mob.services;

import pl.lodz.p.it.ssbd2020.ssbd04.entities.Seat;
import pl.lodz.p.it.ssbd2020.ssbd04.exceptions.AppBaseException;
import pl.lodz.p.it.ssbd2020.ssbd04.interceptors.TrackingInterceptor;
import pl.lodz.p.it.ssbd2020.ssbd04.mob.facades.SeatFacade;

import javax.annotation.security.PermitAll;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;
import javax.interceptor.Interceptors;

/**
 * Przetwarzanie logiki biznesowej siedzeń
 */
@Interceptors({TrackingInterceptor.class})
@Stateless
@TransactionAttribute(TransactionAttributeType.MANDATORY)
public class SeatService {

    @Inject
    private SeatFacade seatFacade;

    /**
     * Zwraca siedzenie o danym identyfikatorze
     *
     * @param id identyfikator siedzenia
     * @return siedzenie
     * @throws AppBaseException gdy operacja nie powiedzie się
     */
    @PermitAll
    public Seat findById(Long id) throws AppBaseException {
        return seatFacade.find(id);
    }

}

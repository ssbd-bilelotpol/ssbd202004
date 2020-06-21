package pl.lodz.p.it.ssbd2020.ssbd04.mob.services;

import pl.lodz.p.it.ssbd2020.ssbd04.entities.Passenger;
import pl.lodz.p.it.ssbd2020.ssbd04.exceptions.AppBaseException;
import pl.lodz.p.it.ssbd2020.ssbd04.interceptors.TrackingInterceptor;
import pl.lodz.p.it.ssbd2020.ssbd04.mob.facades.PassengerFacade;

import javax.annotation.security.PermitAll;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;
import javax.interceptor.Interceptors;
import javax.security.enterprise.SecurityContext;
import java.util.List;

/**
 * Przetwarzanie logiki biznesowej Kont.
 */

@Interceptors({TrackingInterceptor.class})
@Stateless
@TransactionAttribute(TransactionAttributeType.MANDATORY)
public class PassengerService {

    @Inject
    private PassengerFacade passengerFacade;

    @Inject
    private SecurityContext securityContext;

    /**
     * Wyszukuje pasażerów na podstawie przekazanego kryterium.
     * @param name imię i nazwisko pasażera.
     * @param flightCode kod lotniska.
     * @return lotniska spełniające podane kryterium.
     */
    @PermitAll
    public List<Passenger> find(String name, String flightCode) throws AppBaseException {
        return passengerFacade.find(name, flightCode);
    }

}

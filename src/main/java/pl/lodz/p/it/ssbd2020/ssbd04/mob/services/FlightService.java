package pl.lodz.p.it.ssbd2020.ssbd04.mob.services;

import pl.lodz.p.it.ssbd2020.ssbd04.entities.Flight;
import pl.lodz.p.it.ssbd2020.ssbd04.entities.Seat;
import pl.lodz.p.it.ssbd2020.ssbd04.exceptions.AppBaseException;
import pl.lodz.p.it.ssbd2020.ssbd04.interceptors.TrackingInterceptor;
import pl.lodz.p.it.ssbd2020.ssbd04.mob.facades.FlightFacade;
import pl.lodz.p.it.ssbd2020.ssbd04.security.Role;

import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;
import javax.interceptor.Interceptors;
import java.util.List;

/**
 * Przetwarzanie logiki biznesowej lotów.
 */

@Interceptors({TrackingInterceptor.class})
@Stateless
@TransactionAttribute(TransactionAttributeType.MANDATORY)
public class FlightService {

    @Inject
    private FlightFacade flightFacade;

    /**
     * Zwraca loty o podanym identyfikatorze.
     * @param id identyfikator lotu
     * @return lot o podanym identyfikatorze
     * @throws AppBaseException w przypadku niepowodzenia operacji
     */
    @PermitAll
    public Flight findById(Long id) {
        throw new UnsupportedOperationException();
    }

    @RolesAllowed(Role.GetTakenSeats)
    public List<Seat> getTakenSeats(Flight flight) throws AppBaseException {
        throw new UnsupportedOperationException();
    }

}

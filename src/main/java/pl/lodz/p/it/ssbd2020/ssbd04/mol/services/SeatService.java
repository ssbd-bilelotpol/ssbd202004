package pl.lodz.p.it.ssbd2020.ssbd04.mol.services;

import pl.lodz.p.it.ssbd2020.ssbd04.entities.Flight;
import pl.lodz.p.it.ssbd2020.ssbd04.entities.Seat;
import pl.lodz.p.it.ssbd2020.ssbd04.exceptions.AppBaseException;
import pl.lodz.p.it.ssbd2020.ssbd04.interceptors.TrackingInterceptor;
import pl.lodz.p.it.ssbd2020.ssbd04.mol.facades.SeatFacade;
import pl.lodz.p.it.ssbd2020.ssbd04.security.Role;

import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;
import javax.inject.Named;
import javax.interceptor.Interceptors;
import java.util.List;

/**
 * Przetwarzanie logiki biznesowej siedzeń.
 */
@Interceptors({TrackingInterceptor.class})
@Stateless(name = "SeatServiceMOL")
@Named("SeatServiceMOL")
@TransactionAttribute(TransactionAttributeType.MANDATORY)
public class SeatService {

    @Named("SeatFacadeMOL")
    @Inject
    private SeatFacade seatFacade;

    /**
     * Wyszukuje miejsce na podstawie id
     *
     * @param id id miejsca
     * @return obiekt miejsca
     * @throws AppBaseException w przypadku niepowodzenia operacji
     */
    @PermitAll
    public Seat findById(Long id) throws AppBaseException {
        return seatFacade.find(id);
    }

    /**
     * Zwraca listę zajętych siedzeń dla podanego lotu.
     *
     * @param flight lot
     * @return liste siedzeń
     * @throws AppBaseException gdy wystąpi problem z bazą danych.
     */
    @RolesAllowed(Role.GetTakenSeats)
    public List<Seat> getTakenSeats(Flight flight) throws AppBaseException {
        return seatFacade.getTakenSeats(flight.getId());
    }

}

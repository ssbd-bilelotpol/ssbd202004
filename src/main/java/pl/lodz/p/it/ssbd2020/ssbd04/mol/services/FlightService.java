package pl.lodz.p.it.ssbd2020.ssbd04.mol.services;

import pl.lodz.p.it.ssbd2020.ssbd04.common.Config;
import pl.lodz.p.it.ssbd2020.ssbd04.common.I18n;
import pl.lodz.p.it.ssbd2020.ssbd04.entities.*;
import pl.lodz.p.it.ssbd2020.ssbd04.exceptions.AppBaseException;
import pl.lodz.p.it.ssbd2020.ssbd04.exceptions.FlightException;
import pl.lodz.p.it.ssbd2020.ssbd04.interceptors.TrackingInterceptor;
import pl.lodz.p.it.ssbd2020.ssbd04.mol.facades.AirplaneSchemaFacade;
import pl.lodz.p.it.ssbd2020.ssbd04.mol.facades.ConnectionFacade;
import pl.lodz.p.it.ssbd2020.ssbd04.mol.facades.FlightFacade;
import pl.lodz.p.it.ssbd2020.ssbd04.security.Role;
import pl.lodz.p.it.ssbd2020.ssbd04.services.EmailService;

import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;
import javax.interceptor.Interceptors;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static pl.lodz.p.it.ssbd2020.ssbd04.common.I18n.*;

/**
 * Przetwarzanie logiki biznesowej lotów.
 */

@Interceptors({TrackingInterceptor.class})
@Stateless
@TransactionAttribute(TransactionAttributeType.MANDATORY)
public class FlightService {
    @Inject
    private FlightFacade flightFacade;
    @Inject
    private ConnectionFacade connectionFacade;
    @Inject
    private AirplaneSchemaFacade airplaneSchemaFacade;
    @Inject
    private AccountService accountService;
    @Inject
    private EmailService emailService;
    @Inject
    private I18n i18n;
    @Inject
    private Config config;

    /**
     * Wyszukuje loty na podstawie przekazanego kryterium.
     * @param code kod lotu
     * @param connectionId id połączenia
     * @param airplaneId id lotniska
     * @param from data, po której wylatuje lot
     * @param to dat, przed którą wylatuje lot
     * @param flightStatus status lotu
     * @return loty spełniające podane kryterium
     */
    @PermitAll
    public List<Flight> find(String code, Long connectionId, Long airplaneId, LocalDateTime from, LocalDateTime to,
                             FlightStatus flightStatus)
            throws AppBaseException {
        Connection connection = null;
        AirplaneSchema airplaneSchema = null;
        if(connectionId != null) {
            connection = connectionFacade.find(connectionId);
            if(connection == null)
                return new ArrayList<>();
        }
        if(airplaneId != null) {
            airplaneSchema = airplaneSchemaFacade.find(airplaneId);
            if(airplaneSchema == null)
                return new ArrayList<>();
        }
        return flightFacade.find(code, connection, airplaneSchema, from, to, flightStatus);
    }

    @PermitAll
    public List<LocalDate> getDates(LocalDateTime from) throws AppBaseException {
        return flightFacade.getDates(from)
                .stream()
                .map(d -> new java.sql.Date(d.getTime()).toLocalDate())
                .collect(Collectors.toList());
    }

    /**
     * Zwraca loty o podanym identyfikatorze.
     * @param code identyfikator lotu
     * @return lot o podanym identyfikatorze
     * @throws AppBaseException w przypadku niepowodzenia operacji
     */
    @PermitAll
    public Flight findByCode(String code) throws AppBaseException {
        return findByCode(code, false);
    }

    /**
     * Zwraca loty o podanym identyfikatorze.
     * @param code identyfikator lotu
     * @param pessimisticLock czy założyć blokadę pesymistyczną
     * @return lot o podanym identyfikatorze
     * @throws AppBaseException w przypadku niepowodzenia operacji
     */
    @PermitAll
    public Flight findByCode(String code, Boolean pessimisticLock) throws AppBaseException {
        return flightFacade.find(code, pessimisticLock);
    }

    /**
     * Zwraca loty przypisane do danego schematu samolotu
     * @param airplaneSchema schemat samolotu
     * @return lista przypisanych lotów
     * @throws AppBaseException gdy wystapi problem z bazą danych.
     */
    @RolesAllowed(Role.UpdateAirplaneSchema)
    public List<Flight> findByAirplaneSchema(AirplaneSchema airplaneSchema) throws AppBaseException {
        return flightFacade.findByAirplaneSchema(airplaneSchema);
    }

    /**
     * Tworzy i zapisuje w bazie lot.
     * @param flight nowy lot
     * @param connection połączenie, którego lot dotyczy
     * @param airplaneSchema schemat siedzeń przypisany do samolotu
     * @return stworzony lot
     * @throws AppBaseException w przypadku niepowodzenia operacji
     */
    @RolesAllowed(Role.CreateFlight)
    public Flight create(Flight flight, Connection connection, AirplaneSchema airplaneSchema) throws AppBaseException {
        flight.setConnection(connection);
        flight.setAirplaneSchema(airplaneSchema);
        flightFacade.create(flight);
        return flight;
    }

    /**
     * Anuluje lot o podanym identyfikatorze.
     * @param flight lotu do anulowania
     * @throws AppBaseException w przypadku niepowodzenia operacji
     */
    @RolesAllowed(Role.CancelFlight)
    public void cancel(Flight flight) throws AppBaseException {
        if(flight.getStartDateTime().isBefore(LocalDateTime.now(ZoneOffset.UTC))) {
            throw FlightException.alreadyDeparted();
        }
        if(flight.getStatus().equals(FlightStatus.CANCELLED)) {
            throw FlightException.cancelled();
        }
        List<Account> accounts = accountService.getAccountsByTicketsOwnedForFlight(flight.getFlightCode());
        for(Account a : accounts) {
            emailService.sendTransactionalEmail(
                    a.getAccountDetails().getEmail(),
                    i18n.getMessage(FLIGHT_TRACKER_MAIL_SENDER),
                    i18n.getMessage(FLIGHT_CANCELLED_MAIL_TITLE),
                    String.format(i18n.getMessage(FLIGHT_CANCELLED_MAIL_CONTENT),
                            flight.getConnection().getSource().getCity(),
                            flight.getConnection().getDestination().getCity(),
                            flight.getFlightCode(),
                            String.format("%s/panel/tickets/%s", config.getFrontendURL(), flight.getId())));
        }
        flight.setStatus(FlightStatus.CANCELLED);
        flightFacade.edit(flight);
    }

    /**
     * Modyfikuje istniejący lot.
     * @param flight zmodyfikowane dane lotu
     * @param departureTime data wylotu
     * @param arrivalTime data przylotu
     * @throws AppBaseException w przypadku niepowodzenia operacji
     */
    @RolesAllowed(Role.UpdateFlight)
    public void update(Flight flight, LocalDateTime departureTime, LocalDateTime arrivalTime) throws AppBaseException {
        if(flight.getStatus().equals(FlightStatus.CANCELLED)) {
            throw FlightException.cancelled();
        }
        if(flight.getStartDateTime().isBefore(LocalDateTime.now(ZoneOffset.UTC))) {
            throw FlightException.alreadyDeparted();
        }
        if(flight.getStartDateTime().isAfter(departureTime) || flight.getEndDateTime().isAfter(arrivalTime)) {
            throw FlightException.cantMakeEarlier();
        }
        if(flight.getStartDateTime().isBefore(departureTime) || flight.getEndDateTime().isBefore(arrivalTime)) {
            List<Account> accounts = accountService.getAccountsByTicketsOwnedForFlight(flight.getFlightCode());
            for(Account a : accounts) {
                emailService.sendTransactionalEmail(
                        a.getAccountDetails().getEmail(),
                        i18n.getMessage(FLIGHT_TRACKER_MAIL_SENDER),
                        i18n.getMessage(FLIGHT_DELAYED_MAIL_TITLE),
                        String.format(i18n.getMessage(FLIGHT_DELAYED_MAIL_CONTENT),
                                flight.getConnection().getSource().getCity(),
                                flight.getConnection().getDestination().getCity(),
                                flight.getFlightCode(),
                                String.format("%s/panel/tickets/%s", config.getFrontendURL(), flight.getId())));
            }
        }
        flight.setStartDateTime(departureTime);
        flight.setEndDateTime(arrivalTime);
        flight.setModifiedBy(accountService.getCurrentUser());
        flightFacade.edit(flight);
    }
}

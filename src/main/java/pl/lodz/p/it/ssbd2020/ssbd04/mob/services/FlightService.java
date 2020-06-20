package pl.lodz.p.it.ssbd2020.ssbd04.mob.services;

import pl.lodz.p.it.ssbd2020.ssbd04.entities.AirplaneSchema;
import pl.lodz.p.it.ssbd2020.ssbd04.entities.Connection;
import pl.lodz.p.it.ssbd2020.ssbd04.entities.Flight;
import pl.lodz.p.it.ssbd2020.ssbd04.entities.Seat;
import pl.lodz.p.it.ssbd2020.ssbd04.exceptions.AppBaseException;
import pl.lodz.p.it.ssbd2020.ssbd04.interceptors.TrackingInterceptor;
import pl.lodz.p.it.ssbd2020.ssbd04.mob.facades.AirplaneSchemaFacade;
import pl.lodz.p.it.ssbd2020.ssbd04.mob.facades.ConnectionFacade;
import pl.lodz.p.it.ssbd2020.ssbd04.mob.facades.FlightFacade;
import pl.lodz.p.it.ssbd2020.ssbd04.security.Role;

import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;
import javax.interceptor.Interceptors;
import java.time.LocalDateTime;
import java.util.ArrayList;
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

    @Inject
    ConnectionFacade connectionFacade;

    @Inject
    AirplaneSchemaFacade airplaneSchemaFacade;

    /**
     * Zwraca loty o podanym identyfikatorze.
     * @param code identyfikator lotu
     * @return lot o podanym identyfikatorze
     * @throws AppBaseException w przypadku niepowodzenia operacji
     */
    @PermitAll
    public Flight findByCode(String code) throws AppBaseException {
        return flightFacade.find(code);
    }

    /**
     * Wyszukuje loty na podstawie przekazanego kryterium.
     * @param code kod lotu
     * @param connectionId id połączenia
     * @param airplaneId id lotniska
     * @param from data, po której wylatuje lot
     * @param to dat, przed którą wylatuje lot
     * @return loty spełniające podane kryterium
     */
    @PermitAll
    public List<Flight> find(String code, Long connectionId, Long airplaneId)
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
        return flightFacade.find(code, connection, airplaneSchema);
    }

}

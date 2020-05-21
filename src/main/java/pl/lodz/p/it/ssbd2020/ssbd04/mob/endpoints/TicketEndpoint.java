package pl.lodz.p.it.ssbd2020.ssbd04.mob.endpoints;

import pl.lodz.p.it.ssbd2020.ssbd04.interceptors.TrackingInterceptor;

import javax.ejb.Stateful;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.interceptor.Interceptors;

/**
 * Wykonuje konwersję klas DTO na model biznesowy
 * i jest granicą transakcji aplikacyjnej dla hierarchii klas Ticket.
 */

@Interceptors({TrackingInterceptor.class})
@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
@Stateful
public class TicketEndpoint {
}
package pl.lodz.p.it.ssbd2020.ssbd04.mol.services;

import pl.lodz.p.it.ssbd2020.ssbd04.entities.Benefit;
import pl.lodz.p.it.ssbd2020.ssbd04.entities.SeatClass;
import pl.lodz.p.it.ssbd2020.ssbd04.exceptions.AppBaseException;
import pl.lodz.p.it.ssbd2020.ssbd04.interceptors.TrackingInterceptor;
import pl.lodz.p.it.ssbd2020.ssbd04.mol.facades.SeatClassFacade;
import pl.lodz.p.it.ssbd2020.ssbd04.security.Role;

import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;
import javax.interceptor.Interceptors;
import java.util.List;
import java.util.Set;

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
     * @param name nazwa klasy miejsc.
     * @return dane klasy miejsc.
     * @throws AppBaseException gdy operacja nie powiedzie się, bądź klasa miejsc nie istnieje.
     */
    @PermitAll
    public SeatClass findByName(String name) throws AppBaseException {
        throw new UnsupportedOperationException();
    }

    /**
     * Zwraca wszystkie dostępne klasy miejsc, które mogą zostać przypisane do siedzeń.
     *
     * @return listę wszystkich klas miejsc.
     */
    @PermitAll
    public List<SeatClass> getAll() throws AppBaseException {
        throw new UnsupportedOperationException();
    }

    /**
     * Tworzy nową klasę miejsc.
     *
     * @param seatClass dane klasy miejsc.
     * @param benefits  lista dodatków.
     * @return utworzoną klasę miejsc.
     * @throws AppBaseException gdy nazwa klasy miejsc jest już zajęta, bądź operacja nie powiodła się.
     */
    @RolesAllowed(Role.CreateSeatClass)
    public void create(SeatClass seatClass, Set<Benefit> benefits) throws AppBaseException {
        throw new UnsupportedOperationException();
    }

    /**
     * Usuwa klasę miejsc.
     *
     * @param seatClass klasa miejsc.
     * @throws AppBaseException gdy klasa miejsc nie istnieje, jest używana przez siedzenie, bądź operacja nie powiodła się.
     */
    @RolesAllowed(Role.DeleteSeatClass)
    public void delete(SeatClass seatClass) throws AppBaseException {
        throw new UnsupportedOperationException();
    }

    /**
     * Aktualizuję klasę miejsc.
     *
     * @param seatClass klasa miejsc.
     * @throws AppBaseException gdy klasa miejsc nie istnieje, bądź operacja nie powiodła się.
     */
    @RolesAllowed(Role.UpdateSeatClass)
    public void update(SeatClass seatClass) throws AppBaseException {
        throw new UnsupportedOperationException();
    }
}

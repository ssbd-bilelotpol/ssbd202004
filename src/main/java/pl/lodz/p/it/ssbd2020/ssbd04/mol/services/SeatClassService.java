package pl.lodz.p.it.ssbd2020.ssbd04.mol.services;

import pl.lodz.p.it.ssbd2020.ssbd04.entities.Benefit;
import pl.lodz.p.it.ssbd2020.ssbd04.entities.Seat;
import pl.lodz.p.it.ssbd2020.ssbd04.entities.SeatClass;
import pl.lodz.p.it.ssbd2020.ssbd04.exceptions.AppBaseException;
import pl.lodz.p.it.ssbd2020.ssbd04.exceptions.SeatClassException;
import pl.lodz.p.it.ssbd2020.ssbd04.interceptors.TrackingInterceptor;
import pl.lodz.p.it.ssbd2020.ssbd04.mol.facades.BenefitFacade;
import pl.lodz.p.it.ssbd2020.ssbd04.mol.facades.SeatClassFacade;
import pl.lodz.p.it.ssbd2020.ssbd04.mol.facades.SeatFacade;
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

    @Inject
    private BenefitFacade benefitFacade;

    @Inject
    private SeatFacade seatFacade;

    /**
     * Znajduje klasę miejsc na podstawie jej nazwy.
     *
     * @param name nazwa klasy miejsc.
     * @return dane klasy miejsc.
     * @throws AppBaseException gdy operacja nie powiedzie się, bądź klasa miejsc nie istnieje.
     */
    @RolesAllowed(Role.FindSeatClassByName)
    public SeatClass findByName(String name) throws AppBaseException {
        return seatClassFacade.findByName(name);
    }

    /**
     * Zwraca wszystkie dostępne klasy miejsc, które mogą zostać przypisane do siedzeń.
     *
     * @return listę wszystkich klas miejsc.
     */
    @PermitAll
    public List<SeatClass> getAll() throws AppBaseException {
        return seatClassFacade.findAll();
    }

    /**
     * Zwraca wszystkie dostępne benefity, które mogą zostać przypisane do klasy miejsc.
     *
     * @return listę wszystkich klas miejsc.
     */
    @RolesAllowed(Role.GetAllBenefits)
    public List<Benefit> getAllBenefits() throws AppBaseException {
        return benefitFacade.findAll();
    }

    /**
     * Tworzy nową klasę miejsc.
     *
     * @param seatClass dane klasy miejsc.
     * @param existingBenefits  lista dodatków.
     * @return utworzoną klasę miejsc.
     * @throws AppBaseException gdy nazwa klasy miejsc jest już zajęta, bądź operacja nie powiodła się.
     */
    @RolesAllowed(Role.CreateSeatClass)
    public SeatClass create(SeatClass seatClass, Set<Benefit> existingBenefits) throws AppBaseException {
        seatClassFacade.create(addExistingBenefits(seatClass, existingBenefits));
        return seatClass;
    }

    /**
     * Usuwa klasę miejsc.
     *
     * @param seatClass klasa miejsc.
     * @throws AppBaseException gdy klasa miejsc nie istnieje, jest używana przez siedzenie, bądź operacja nie powiodła się.
     */
    @RolesAllowed(Role.DeleteSeatClass)
    public void delete(SeatClass seatClass) throws AppBaseException {
        seatClassFacade.remove(seatClass);
    }

    /**
     * Aktualizuję klasę miejsc.
     *
     * @param seatClass klasa miejsc.
     * @throws AppBaseException gdy klasa miejsc nie istnieje, bądź operacja nie powiodła się.
     */
    @RolesAllowed(Role.UpdateSeatClass)
    public SeatClass update(SeatClass seatClass, Set<Benefit> existingBenefits) throws AppBaseException {
        List<Seat> seats = seatFacade.findBySeatClass(seatClass);
        if (seats.size() > 0) throw SeatClassException.inUse(seatClass);
        seatClassFacade.edit(addExistingBenefits(seatClass, existingBenefits));
        return seatClass;
    }

    private SeatClass addExistingBenefits(SeatClass seatClass, Set<Benefit> benefits) throws AppBaseException {
        for (Benefit b : benefits) {
            Benefit benefit = benefitFacade.findByName(b.getName());
            seatClass.getBenefits().add(benefit);
        }
        return seatClass;
    }
}

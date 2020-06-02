package pl.lodz.p.it.ssbd2020.ssbd04.mol.endpoints;

import pl.lodz.p.it.ssbd2020.ssbd04.common.AbstractEndpoint;
import pl.lodz.p.it.ssbd2020.ssbd04.entities.Benefit;
import pl.lodz.p.it.ssbd2020.ssbd04.entities.SeatClass;
import pl.lodz.p.it.ssbd2020.ssbd04.exceptions.AppBaseException;
import pl.lodz.p.it.ssbd2020.ssbd04.interceptors.TrackingInterceptor;
import pl.lodz.p.it.ssbd2020.ssbd04.mol.dto.BenefitDto;
import pl.lodz.p.it.ssbd2020.ssbd04.mol.dto.SeatClassDto;
import pl.lodz.p.it.ssbd2020.ssbd04.mol.facades.BenefitFacade;
import pl.lodz.p.it.ssbd2020.ssbd04.mol.services.SeatClassService;
import pl.lodz.p.it.ssbd2020.ssbd04.security.Role;

import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;
import javax.ejb.Stateful;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;
import javax.interceptor.Interceptors;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Wykonuje konwersję klas DTO na model biznesowy
 * i jest granicą transakcji aplikacyjnej dla hierarchii klas SeatClass.
 */
@Interceptors({TrackingInterceptor.class})
@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
@Stateful
public class SeatClassEndpointImpl extends AbstractEndpoint implements SeatClassEndpoint {
    @Inject
    private SeatClassService seatClassService;

    @Inject
    private BenefitFacade benefitFacade;

    @Override
    @RolesAllowed(Role.FindSeatClassByName)
    public SeatClassDto findByName(String name) throws AppBaseException {
        SeatClass seatClass = seatClassService.findByName(name);
        return new SeatClassDto(seatClass);
    }

    @Override
    @RolesAllowed(Role.GetAllBenefits)
    public List<BenefitDto> getAllBenefits() throws AppBaseException {
        return seatClassService.getAllBenefits().stream()
                .map(b -> new BenefitDto(b))
                .collect(Collectors.toList());
    }

    @Override
    @RolesAllowed(Role.GetAllSeatClasses)
    public List<SeatClassDto> getAll() throws AppBaseException {
        return seatClassService.getAll().stream()
                .map(sc -> new SeatClassDto(sc))
                .collect(Collectors.toList());
    }

    @Override
    @RolesAllowed(Role.CreateSeatClass)
    public SeatClassDto create(SeatClassDto seatClassDto) throws AppBaseException {
        Set<Benefit> benefits = seatClassDto.getBenefits().stream()
                .map(b -> new Benefit(b.getName(), b.getDescription()))
                .collect(Collectors.toSet());
        SeatClass seatClass = new SeatClass();
        seatClass.setName(seatClassDto.getName());
        seatClass.setPrice(seatClassDto.getPrice());
        return new SeatClassDto(seatClassService.create(seatClass, benefits));
    }

    @Override
    @RolesAllowed(Role.DeleteSeatClass)
    public void delete(String name) throws AppBaseException {
        // throws: SeatClassNotFound
        throw new UnsupportedOperationException();
    }

    @Override
    @RolesAllowed(Role.UpdateSeatClass)
    public void update(SeatClassDto seatClassDto) throws AppBaseException {
        // throws: OptimisticLockException
        // throws: SeatClassNotFound
        throw new UnsupportedOperationException();
    }
}

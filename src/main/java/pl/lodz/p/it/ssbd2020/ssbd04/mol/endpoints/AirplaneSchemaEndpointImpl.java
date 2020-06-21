package pl.lodz.p.it.ssbd2020.ssbd04.mol.endpoints;

import pl.lodz.p.it.ssbd2020.ssbd04.common.AbstractEndpoint;
import pl.lodz.p.it.ssbd2020.ssbd04.entities.AirplaneSchema;
import pl.lodz.p.it.ssbd2020.ssbd04.entities.Seat;
import pl.lodz.p.it.ssbd2020.ssbd04.entities.SeatClass;
import pl.lodz.p.it.ssbd2020.ssbd04.exceptions.AirplaneSchemaException;
import pl.lodz.p.it.ssbd2020.ssbd04.exceptions.AppBaseException;
import pl.lodz.p.it.ssbd2020.ssbd04.interceptors.TrackingInterceptor;
import pl.lodz.p.it.ssbd2020.ssbd04.mol.dto.AirplaneSchemaDto;
import pl.lodz.p.it.ssbd2020.ssbd04.mol.dto.AirplaneSchemaListDto;
import pl.lodz.p.it.ssbd2020.ssbd04.mol.dto.SeatDto;
import pl.lodz.p.it.ssbd2020.ssbd04.mol.services.AirplaneSchemaService;
import pl.lodz.p.it.ssbd2020.ssbd04.mol.services.SeatClassService;
import pl.lodz.p.it.ssbd2020.ssbd04.security.Role;

import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;
import javax.ejb.Stateful;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;
import javax.interceptor.Interceptors;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Wykonuje konwersję klas DTO na model biznesowy
 * i jest granicą transakcji aplikacyjnej dla hierarchii klas AirplaneSchema.
 */
@Interceptors({TrackingInterceptor.class})
@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
@Stateful
public class AirplaneSchemaEndpointImpl extends AbstractEndpoint implements AirplaneSchemaEndpoint {
    @Inject
    private AirplaneSchemaService airplaneSchemaService;

    @Inject
    private SeatClassService seatClassService;

    @Override
    @RolesAllowed(Role.GetAllAirplaneSchemas)
    public List<AirplaneSchemaListDto> findByName(String name) throws AppBaseException {
        return airplaneSchemaService.findByName(name)
                .stream()
                .map(AirplaneSchemaListDto::new)
                .collect(Collectors.toList());
    }

    @Override
    @PermitAll
    public AirplaneSchemaDto findById(Long id) throws AppBaseException {
        return new AirplaneSchemaDto(airplaneSchemaService.findById(id));
    }


    @Override
    @RolesAllowed(Role.CreateAirplaneSchema)
    public AirplaneSchemaDto create(AirplaneSchemaDto airplaneSchemaDto) throws AppBaseException {
        AirplaneSchema airplaneSchema = new AirplaneSchema();
        airplaneSchema.setName(airplaneSchemaDto.getName());
        airplaneSchema.setRows(airplaneSchemaDto.getRows());
        airplaneSchema.setCols(airplaneSchemaDto.getColumns());
        Map<String, SeatClass> seatClassMap = new HashMap<>();
        seatClassService.getAll().forEach(seatClass -> seatClassMap.put(seatClass.getName(), seatClass));

        Set<Seat> seats = new HashSet<>();
        for (SeatDto seatDto : airplaneSchemaDto.getSeats()) {
            Seat seat = new Seat();
            seat.setAirplaneSchema(airplaneSchema);
            seat.setCol(seatDto.getCol());
            seat.setRow(seatDto.getRow());
            seat.setSeatClass(seatClassMap.get(seatDto.getSeatClass()));
            seats.add(seat);
        }
        return new AirplaneSchemaDto(airplaneSchemaService.create(airplaneSchema, airplaneSchemaDto.getEmptyColumns(), airplaneSchemaDto.getEmptyRows(), seats));
    }

    private Set<Seat> updatedSeats(AirplaneSchema airplaneSchema, AirplaneSchemaDto airplaneSchemaDto) throws AppBaseException {
        Map<String, SeatClass> seatClassMap = new HashMap<>();
        seatClassService.getAll().forEach(seatClass -> seatClassMap.put(seatClass.getName(), seatClass));

        Map<Long, Seat> seats = new HashMap<>();
        for (Seat seat : airplaneSchema.getSeatList()) {
            seats.put(seat.getId(), seat);
        }

        Set<Seat> newSeats = new HashSet<>();
        for (SeatDto seatDto : airplaneSchemaDto.getSeats()) {
            Seat seat = seats.get(seatDto.getId());
            SeatClass seatClass = seatClassMap.get(seatDto.getSeatClass());
            if (seat != null) {
                seat.setSeatClass(seatClass);
            } else {
                seat = new Seat();
                seat.setSeatClass(seatClass);
                seat.setCol(seatDto.getCol());
                seat.setRow(seatDto.getRow());
                seat.setAirplaneSchema(airplaneSchema);
            }
            newSeats.add(seat);
        }

        return newSeats;
    }

    @Override
    @RolesAllowed(Role.UpdateAirplaneSchema)
    public void update(Long id, AirplaneSchemaDto airplaneSchemaDto) throws AppBaseException {
        AirplaneSchema airplaneSchema = airplaneSchemaService.findById(id);
        if (airplaneSchema == null) {
            throw AirplaneSchemaException.notFound();
        }
        AirplaneSchemaDto currentAirplaneSchemaDto = new AirplaneSchemaDto(airplaneSchema);
        if (!verifyEtag(currentAirplaneSchemaDto)) {
            throw AppBaseException.optimisticLock();
        }

        airplaneSchema.setName(airplaneSchemaDto.getName());
        airplaneSchema.setRows(airplaneSchemaDto.getRows());
        airplaneSchema.setCols(airplaneSchemaDto.getColumns());
        airplaneSchemaService.update(
                airplaneSchema,
                airplaneSchemaDto.getEmptyColumns(),
                airplaneSchemaDto.getEmptyRows(),
                updatedSeats(airplaneSchema, airplaneSchemaDto)
        );
    }

    @Override
    @RolesAllowed(Role.DeleteAirplaneSchema)
    public void delete(Long id) throws AppBaseException {
        AirplaneSchema airplaneSchema = airplaneSchemaService.findById(id);
        if (airplaneSchema == null) {
            throw AirplaneSchemaException.notFound();
        }
        AirplaneSchemaDto dto = new AirplaneSchemaDto(airplaneSchema);
        if (!verifyEtag(dto)) {
            throw AppBaseException.optimisticLock();
        }

        airplaneSchemaService.delete(airplaneSchema);
    }
}

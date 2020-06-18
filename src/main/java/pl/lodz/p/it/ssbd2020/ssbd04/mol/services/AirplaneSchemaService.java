package pl.lodz.p.it.ssbd2020.ssbd04.mol.services;

import pl.lodz.p.it.ssbd2020.ssbd04.entities.AirplaneSchema;
import pl.lodz.p.it.ssbd2020.ssbd04.entities.Flight;
import pl.lodz.p.it.ssbd2020.ssbd04.entities.Seat;
import pl.lodz.p.it.ssbd2020.ssbd04.exceptions.AirplaneSchemaException;
import pl.lodz.p.it.ssbd2020.ssbd04.exceptions.AppBaseException;
import pl.lodz.p.it.ssbd2020.ssbd04.exceptions.FlightException;
import pl.lodz.p.it.ssbd2020.ssbd04.interceptors.TrackingInterceptor;
import pl.lodz.p.it.ssbd2020.ssbd04.mol.dto.SeatDto;
import pl.lodz.p.it.ssbd2020.ssbd04.mol.facades.AirplaneSchemaFacade;
import pl.lodz.p.it.ssbd2020.ssbd04.mol.facades.TicketFacade;
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
import java.util.stream.Collectors;

/**
 * Przetwarzanie logiki biznesowej schematów samolotów.
 */
@Interceptors({TrackingInterceptor.class})
@Stateless
@TransactionAttribute(TransactionAttributeType.MANDATORY)
public class AirplaneSchemaService {
    @Inject
    private AirplaneSchemaFacade airplaneSchemaFacade;

    @Inject
    private TicketFacade ticketFacade;

    @Inject
    private AccountService accountService;

    @Inject
    private FlightService flightService;

    private void validateRules(AirplaneSchema airplaneSchema, Set<Integer> emptyColumns, Set<Integer> emptyRows, Set<Seat> seats) throws AppBaseException {
        for (Integer column : emptyColumns) {
            if (column <= 0 || column >= airplaneSchema.getCols()) {
                throw AirplaneSchemaException.invalidData();
            }
        }
        for (Integer row : emptyRows) {
            if (row <= 0 || row >= airplaneSchema.getRows()) {
                throw AirplaneSchemaException.invalidData();
            }
        }

        for (Seat seat : seats) {
            if (seat.getRow() > airplaneSchema.getRows() || seat.getCol() > airplaneSchema.getCols()) {
                throw AirplaneSchemaException.invalidData();
            }
        }
        if (seats.size() != airplaneSchema.getCols() * airplaneSchema.getRows()) {
            throw AirplaneSchemaException.invalidData();
        }
    }

    /**
     * Tworzy nowy schemat samolotu z ustalonymi miejscami.
     *
     * @param airplaneSchema encja schematu samolotu.
     * @param seats
     * @return stworzony schemat samolotu.
     * @throws AppBaseException gdy operacja tworzenia nie powiedzie się.
     */
    @RolesAllowed(Role.CreateAirplaneSchema)
    public AirplaneSchema create(AirplaneSchema airplaneSchema, Set<Integer> emptyColumns, Set<Integer> emptyRows, Set<Seat> seats) throws AppBaseException {
        validateRules(airplaneSchema, emptyColumns, emptyRows, seats);
        airplaneSchema.setEmptyColumns(join(emptyColumns));
        airplaneSchema.setEmptyRows(join(emptyRows));
        airplaneSchema.setSeatList(seats);
        airplaneSchema.setCreatedBy(accountService.getCurrentUser());
        airplaneSchemaFacade.create(airplaneSchema);

        return airplaneSchema;
    }

    private String join(Set<Integer> nums) {
        return nums.stream().map(String::valueOf).collect(Collectors.joining(","));
    }

    /**
     * Pobiera wszystkie istniejące schematy samolotów.
     *
     * @return listę schematów samolotów.
     * @throws AppBaseException gdy wystąpi błąd podczas pobierania z bazy danych.
     */
    @RolesAllowed(Role.GetAllAirplaneSchemas)
    public List<AirplaneSchema> getAll() throws AppBaseException {
        throw new UnsupportedOperationException();
    }

    /**
     * Znajduje schemat samolotu na podstawie identyfikatora.
     *
     * @param id identyfikator schematu samolotu.
     * @return znaleziony schemat samolotu.
     * @throws AppBaseException gdy operacja nie powiedzie się, bądź schemat nie został znaleziony.
     */
    @PermitAll
    public AirplaneSchema findById(Long id) throws AppBaseException {
        return airplaneSchemaFacade.find(id);
    }

    /**
     * Aktualizuje schemat samolotu.
     *
     * @param airplaneSchema dane schematu samolotu.
     * @param emptyColumns lista pozycji na których znajdująsię puste przejścia w kolumnach
     * @param emptyRows lista pozycji na których znajdująsię puste przejścia w wierszach
     * @return zaaktualizowany schemat samolotu.
     * @throws AppBaseException gdy schemat jest już używany, zajdzie problem konkurencyjnej modyfikacji.
     */
    @RolesAllowed(Role.UpdateAirplaneSchema)
    public void update(AirplaneSchema airplaneSchema, Set<Integer> emptyColumns, Set<Integer> emptyRows, Set<Seat> seats) throws AppBaseException {
        validateRules(airplaneSchema, emptyColumns, emptyRows, seats);
        Set<Seat> newSeats = airplaneSchema.getSeatList();
        newSeats.clear();
        newSeats.addAll(seats);
        airplaneSchema.setSeatList(newSeats);
        airplaneSchema.setEmptyColumns(join(emptyColumns));
        airplaneSchema.setEmptyRows(join(emptyRows));
        airplaneSchema.setModifiedBy(accountService.getCurrentUser());

        if (flightService.findByAirplaneSchema(airplaneSchema).size() > 0) {
            throw AirplaneSchemaException.inUse(airplaneSchema);
        }

        airplaneSchemaFacade.edit(airplaneSchema);
    }

    /**
     * Usuwa schemat samolotu.
     *
     * @param airplaneSchema schemat samolotu.
     * @throws AppBaseException gdy schemat jest używany przez pewien lot.
     */
    @RolesAllowed(Role.DeleteAirplaneSchema)
    public void delete(AirplaneSchema airplaneSchema) throws AppBaseException {
        airplaneSchemaFacade.remove(airplaneSchema);
    }

    /**
     * Szuka schematów samolotów których nazwa pasuje do podanej nazwy.
     * @param name podana nazwa
     * @return listę schematów samolotów
     */
    @RolesAllowed(Role.GetAllAirplaneSchemas)
    public List<AirplaneSchema> findByName(String name) throws AppBaseException {
        return airplaneSchemaFacade.findByName(name);
    }
}

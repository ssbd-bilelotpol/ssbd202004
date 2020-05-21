package pl.lodz.p.it.ssbd2020.ssbd04.mol.services;

import pl.lodz.p.it.ssbd2020.ssbd04.entities.AirplaneSchema;
import pl.lodz.p.it.ssbd2020.ssbd04.exceptions.AppBaseException;
import pl.lodz.p.it.ssbd2020.ssbd04.interceptors.TrackingInterceptor;
import pl.lodz.p.it.ssbd2020.ssbd04.mol.facades.AirplaneSchemaFacade;
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
 * Przetwarzanie logiki biznesowej schematów samolotów.
 */
@Interceptors({TrackingInterceptor.class})
@Stateless
@TransactionAttribute(TransactionAttributeType.MANDATORY)
public class AirplaneSchemaService {
    @Inject
    private AirplaneSchemaFacade airplaneSchemaFacade;

    /**
     * Tworzy nowy schemat samolotu z ustalonymi miejscami.
     *
     * @param airplaneSchema encja schematu samolotu.
     * @return stworzony schemat samolotu.
     * @throws AppBaseException gdy operacja tworzenia nie powiedzie się.
     */
    @RolesAllowed(Role.CreateAirplaneSchema)
    public AirplaneSchema create(AirplaneSchema airplaneSchema) throws AppBaseException {
        throw new UnsupportedOperationException();
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
        throw new UnsupportedOperationException();
    }

    /**
     * Aktualizuje schemat samolotu.
     *
     * @param airplaneSchema dane schematu samolotu.
     * @return zaaktualizowany schemat samolotu.
     * @throws AppBaseException gdy schemat jest już używany, zajdzie problem konkurencyjnej modyfikacji.
     */
    @RolesAllowed(Role.UpdateAirplaneSchema)
    public void update(AirplaneSchema airplaneSchema) throws AppBaseException {
        // throws: AirplaneSchemaInUse (is attached to a flight)
        throw new UnsupportedOperationException();
    }

    /**
     * Usuwa schemat samolotu.
     *
     * @param airplaneSchema schemat samolotu.
     * @throws AppBaseException gdy schemat jest używany przez pewien lot..
     */
    @RolesAllowed(Role.DeleteAirplaneSchema)
    public void delete(AirplaneSchema airplaneSchema) throws AppBaseException {
        // throws: AirplaneSchemaInUse (is attached to a flight)
        throw new UnsupportedOperationException();
    }
}

package pl.lodz.p.it.ssbd2020.ssbd04.mol.services;

import pl.lodz.p.it.ssbd2020.ssbd04.entities.Airport;
import pl.lodz.p.it.ssbd2020.ssbd04.exceptions.AppBaseException;
import pl.lodz.p.it.ssbd2020.ssbd04.interceptors.TrackingInterceptor;
import pl.lodz.p.it.ssbd2020.ssbd04.mol.dto.AirportQueryDto;
import pl.lodz.p.it.ssbd2020.ssbd04.mol.facades.AirportFacade;
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
 * Przetwarzanie logiki biznesowej lotnisk.
 */
@Interceptors({TrackingInterceptor.class})
@Stateless
@TransactionAttribute(TransactionAttributeType.MANDATORY)
public class AirportService {
    @Inject
    private AirportFacade airportFacade;

    /**
     * Wyszukuje lotniska na podstawie przekazanego kryterium.
     * @param query kryterium
     * @return lotniska spełniające podane kryterium
     */
    @PermitAll
    public List<Airport> find(AirportQueryDto query) {
        throw new UnsupportedOperationException();
    }

    /**
     * Zwraca lotnisko o podanym identyfikatorze.
     * @param id identyfikator lotniska
     * @return lotnisko o podanym identyfikatorze
     * @throws AppBaseException w przypadku niepowodzenia operacji
     */
    @PermitAll
    public Airport findById(Long id) {
        throw new UnsupportedOperationException();
    }

    /**
     * Zapisuje w bazie lotnisko.
     * @param airport lotnisko do zapisania.
     * @return stworzone lotnisko
     * @throws AppBaseException w przypadku niepowodzenia operacji
     */
    @RolesAllowed(Role.CreateAirport)
    public Airport create(Airport airport) throws AppBaseException {
        throw new UnsupportedOperationException();
    }

    /**
     * Usuwa lotnisko o podanym identyfikatorze.
     * @param airport lotnisko do usunięcia
     * @throws AppBaseException w przypadku niepowodzenia operacji
     */
    @RolesAllowed(Role.DeleteAirport)
    public void delete(Airport airport) throws AppBaseException {
        throw new UnsupportedOperationException();
    }

    /**
     * Modyfikuje istniejące lotnisko.
     * @param airport zmodyfikowane dane lotniska
     * @throws AppBaseException w przypadku niepowodzenia operacji
     */
    @RolesAllowed(Role.UpdateAirport)
    public void update(Airport airport) throws AppBaseException {
        throw new UnsupportedOperationException();
    }
}

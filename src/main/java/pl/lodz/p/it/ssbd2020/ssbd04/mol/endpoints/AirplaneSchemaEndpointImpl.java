package pl.lodz.p.it.ssbd2020.ssbd04.mol.endpoints;

import pl.lodz.p.it.ssbd2020.ssbd04.common.AbstractEndpoint;
import pl.lodz.p.it.ssbd2020.ssbd04.exceptions.AppBaseException;
import pl.lodz.p.it.ssbd2020.ssbd04.interceptors.TrackingInterceptor;
import pl.lodz.p.it.ssbd2020.ssbd04.mol.dto.AirplaneSchemaDto;
import pl.lodz.p.it.ssbd2020.ssbd04.mol.services.AirplaneSchemaService;
import pl.lodz.p.it.ssbd2020.ssbd04.security.Role;

import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;
import javax.ejb.Stateful;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;
import javax.interceptor.Interceptors;
import java.util.List;

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

    @Override
    @RolesAllowed(Role.CreateAirplaneSchema)
    public AirplaneSchemaDto create(AirplaneSchemaDto airplaneSchemaDto) throws AppBaseException {
        throw new UnsupportedOperationException();
    }

    @Override
    @RolesAllowed(Role.GetAllAirplaneSchemas)
    public List<AirplaneSchemaDto> getAll() {
        throw new UnsupportedOperationException();
    }

    @Override
    @PermitAll
    public AirplaneSchemaDto findById(Long id) throws AppBaseException {
        throw new UnsupportedOperationException();
    }

    @Override
    @RolesAllowed(Role.UpdateAirplaneSchema)
    public AirplaneSchemaDto update(AirplaneSchemaDto airplaneSchemaDto) throws AppBaseException {
        // throws: OptimisticLockException
        // throws: AirplaneSchemaNotFound
        throw new UnsupportedOperationException();
    }


    @Override
    @RolesAllowed(Role.DeleteAirplaneSchema)
    public void delete(Long id) throws AppBaseException {
        // throws: AirplaneSchemaNotFound
        throw new UnsupportedOperationException();
    }

}

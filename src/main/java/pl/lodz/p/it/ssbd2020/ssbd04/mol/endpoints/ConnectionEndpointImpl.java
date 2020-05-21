package pl.lodz.p.it.ssbd2020.ssbd04.mol.endpoints;

import pl.lodz.p.it.ssbd2020.ssbd04.exceptions.AppBaseException;
import pl.lodz.p.it.ssbd2020.ssbd04.interceptors.TrackingInterceptor;
import pl.lodz.p.it.ssbd2020.ssbd04.mol.dto.ConnectionDto;
import pl.lodz.p.it.ssbd2020.ssbd04.mol.dto.ConnectionQueryDto;
import pl.lodz.p.it.ssbd2020.ssbd04.mol.services.AirportService;
import pl.lodz.p.it.ssbd2020.ssbd04.mol.services.ConnectionService;
import pl.lodz.p.it.ssbd2020.ssbd04.security.Role;

import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;
import javax.ejb.Stateful;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;
import javax.interceptor.Interceptors;
import java.util.List;

@Interceptors({TrackingInterceptor.class})
@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
@Stateful
public class ConnectionEndpointImpl implements ConnectionEndpoint {
    @Inject
    private ConnectionService connectionService;
    @Inject
    private AirportService airportService;

    @Override
    @PermitAll
    public List<ConnectionDto> find(ConnectionQueryDto query) {
        throw new UnsupportedOperationException();
    }

    @Override
    @PermitAll
    public ConnectionDto findById(Long id) throws AppBaseException {
        throw new UnsupportedOperationException();
    }

    @Override
    @RolesAllowed(Role.CreateConnection)
    public ConnectionDto create(ConnectionDto ConnectionDto) throws AppBaseException {
        throw new UnsupportedOperationException();
    }

    @Override
    @RolesAllowed(Role.DeleteConnection)
    public void delete(Long id) throws AppBaseException {
        throw new UnsupportedOperationException();
    }

    @Override
    @RolesAllowed(Role.UpdateConnection)
    public void update(Long id, ConnectionDto ConnectionDto) throws AppBaseException {
        throw new UnsupportedOperationException();
    }
}

package pl.lodz.p.it.ssbd2020.ssbd04.mol.endpoints;

import pl.lodz.p.it.ssbd2020.ssbd04.common.AbstractEndpoint;
import pl.lodz.p.it.ssbd2020.ssbd04.entities.Connection;
import pl.lodz.p.it.ssbd2020.ssbd04.exceptions.AppBaseException;
import pl.lodz.p.it.ssbd2020.ssbd04.exceptions.ConnectionException;
import pl.lodz.p.it.ssbd2020.ssbd04.interceptors.TrackingInterceptor;
import pl.lodz.p.it.ssbd2020.ssbd04.mol.dto.ConnectionCreateDto;
import pl.lodz.p.it.ssbd2020.ssbd04.mol.dto.ConnectionDto;
import pl.lodz.p.it.ssbd2020.ssbd04.mol.services.AccountService;
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
import java.util.stream.Collectors;

@Interceptors({TrackingInterceptor.class})
@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
@Stateful
public class ConnectionEndpointImpl extends AbstractEndpoint implements ConnectionEndpoint {
    @Inject
    private ConnectionService connectionService;

    @Inject
    private AccountService accountService;

    @Override
    @PermitAll
    public List<ConnectionDto> find(String destinationCode, String sourceCode) throws AppBaseException {
        return connectionService.find(destinationCode, sourceCode).stream().map(ConnectionDto::new).collect(Collectors.toList());
    }

    @Override
    public List<ConnectionDto> findByPhrase(String phrase) throws AppBaseException {
        return connectionService.findByPhrase(phrase).stream().map(ConnectionDto::new).collect(Collectors.toList());
    }

    @Override
    @PermitAll
    public ConnectionDto findById(Long id) throws AppBaseException {
        Connection connection = connectionService.findById(id);
        if (connection == null) {
            throw ConnectionException.notFound();
        }
        return new ConnectionDto(connection);
    }

    @Override
    @RolesAllowed(Role.CreateConnection)
    public ConnectionDto create(ConnectionCreateDto connectionCreateDto) throws AppBaseException {
        Connection connection = new Connection(connectionCreateDto.getBasePrice());
        connection.setCreatedBy(accountService.getCurrentUser());

        return new ConnectionDto(connectionService.create(connection, connectionCreateDto.getDestinationCode(), connectionCreateDto.getSourceCode()));
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

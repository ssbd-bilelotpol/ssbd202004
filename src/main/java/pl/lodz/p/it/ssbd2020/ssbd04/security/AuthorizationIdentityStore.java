package pl.lodz.p.it.ssbd2020.ssbd04.security;

import pl.lodz.p.it.ssbd2020.ssbd04.security.persistence.AuthFacade;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.security.enterprise.identitystore.CredentialValidationResult;
import javax.security.enterprise.identitystore.IdentityStore;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import static java.util.Collections.singleton;
import static javax.security.enterprise.identitystore.IdentityStore.ValidationType.PROVIDE_GROUPS;

/**
 * Zajmuje się autoryzacją klienta na podstawie grup przesłanych w tokenie JWT.
 */
@Stateless
public class AuthorizationIdentityStore implements IdentityStore {
    private Map<String, String> mapper = Map.of(
            "client", Role.Client,
            "customer_service", Role.CustomerService,
            "manager", Role.Manager,
            "admin", Role.Admin
        );

    @Inject
    private AuthFacade authFacade;

    @Override
    public Set<String> getCallerGroups(CredentialValidationResult validationResult) {
        return authFacade.findByLogin(
                validationResult.getCallerPrincipal().getName()
        ).getAccountAccessLevel().stream()
                .map(accountAccessLevel -> mapper.get(accountAccessLevel.getName()) )
                .collect(Collectors.toUnmodifiableSet());
    }

    @Override
    public Set<ValidationType> validationTypes() {
        return singleton(PROVIDE_GROUPS);
    }
}

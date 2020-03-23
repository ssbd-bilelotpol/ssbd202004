package pl.lodz.p.it.ssbd2020.ssbd04.security;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.security.enterprise.credential.Credential;
import javax.security.enterprise.credential.UsernamePasswordCredential;
import javax.security.enterprise.identitystore.CredentialValidationResult;
import javax.security.enterprise.identitystore.IdentityStore;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import static java.util.Collections.singleton;
import static javax.security.enterprise.identitystore.CredentialValidationResult.INVALID_RESULT;
import static javax.security.enterprise.identitystore.CredentialValidationResult.NOT_VALIDATED_RESULT;
import static javax.security.enterprise.identitystore.IdentityStore.ValidationType.VALIDATE;


@RequestScoped
public class AuthenticationIdentityStore implements IdentityStore {

    private Map<String, String> callerToPassword;

    @PostConstruct
    public void init() {
        callerToPassword = new HashMap<>();
        callerToPassword.put("ks", "1234");
    }

    private boolean isValid(UsernamePasswordCredential usernamePassword) {
        return usernamePassword.getPassword().compareTo(callerToPassword.get(usernamePassword.getCaller()));
    }

    @Override
    public CredentialValidationResult validate(Credential credential) {
        if (!(credential instanceof UsernamePasswordCredential)) {
            return NOT_VALIDATED_RESULT;
        }

        UsernamePasswordCredential usernamePassword = (UsernamePasswordCredential) credential;
        if (!isValid(usernamePassword)) {
            return INVALID_RESULT;
        }

        return new CredentialValidationResult(usernamePassword.getCaller());
    }

    @Override
    public Set<ValidationType> validationTypes() {
        return singleton(VALIDATE);
    }
}

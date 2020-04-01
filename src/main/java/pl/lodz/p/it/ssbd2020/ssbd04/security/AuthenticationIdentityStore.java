package pl.lodz.p.it.ssbd2020.ssbd04.security;

import at.favre.lib.crypto.bcrypt.BCrypt;
import pl.lodz.p.it.ssbd2020.ssbd04.security.persistence.Account;
import pl.lodz.p.it.ssbd2020.ssbd04.security.persistence.AuthFacade;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.security.enterprise.credential.Credential;
import javax.security.enterprise.credential.UsernamePasswordCredential;
import javax.security.enterprise.identitystore.CredentialValidationResult;
import javax.security.enterprise.identitystore.IdentityStore;
import java.util.Set;
import static java.util.Collections.singleton;
import static javax.security.enterprise.identitystore.CredentialValidationResult.INVALID_RESULT;
import static javax.security.enterprise.identitystore.CredentialValidationResult.NOT_VALIDATED_RESULT;
import static javax.security.enterprise.identitystore.IdentityStore.ValidationType.VALIDATE;

/**
 * Mechanizm uwierzytelniający klienta na podstawie JWT wysyłanego w nagłówku.
 */
@RequestScoped
public class AuthenticationIdentityStore implements IdentityStore {
    @Inject
    private AuthFacade authFacade;

    private boolean isValid(UsernamePasswordCredential usernamePassword) {
        Account account = authFacade.findByLogin(usernamePassword.getCaller());
        if (account == null) {
            return false;
        }

        return BCrypt.verifyer().verify(
                        usernamePassword.getPassword().getValue(),
                        account.getPassword()
                ).verified;
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

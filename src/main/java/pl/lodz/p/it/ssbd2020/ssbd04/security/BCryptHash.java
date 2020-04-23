package pl.lodz.p.it.ssbd2020.ssbd04.security;

import at.favre.lib.crypto.bcrypt.BCrypt;

import javax.security.enterprise.identitystore.PasswordHash;
import java.util.Map;

/**
 * Adapter umożliwiający wykorzystanie algorytmu BCrypt w IdentityStore
 */
public class BCryptHash implements PasswordHash {
    private int numRounds;

    @Override
    public void initialize(Map<String, String> parameters) {
        numRounds = Integer.parseInt(parameters.get("numRounds"));
    }

    @Override
    public String generate(char[] password) {
        return BCrypt.withDefaults().hashToString(numRounds, password);
    }

    @Override
    public boolean verify(char[] password, String hashedPassword) {
        return BCrypt.verifyer().verify(password, hashedPassword).verified;
    }
}

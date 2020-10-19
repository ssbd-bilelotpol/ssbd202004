package pl.lodz.p.it.ssbd2020.ssbd04.security;

import javax.security.enterprise.identitystore.DatabaseIdentityStoreDefinition;

/**
 * Definiuje repozytorium tożsamości używane do uwierzytelnienia i autoryzacji poprzez Soteria API.
 */
@DatabaseIdentityStoreDefinition(
        dataSourceLookup = "jdbc/ssbd04authDS",
        callerQuery = "SELECT DISTINCT password FROM auth_view WHERE login = ?",
        groupsQuery = "SELECT access_level FROM auth_view WHERE login = ?",
        hashAlgorithm = BCryptHash.class,
        hashAlgorithmParameters = "numRounds=12"
)
public class IdentityStore {

}

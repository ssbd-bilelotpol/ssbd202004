package pl.lodz.p.it.ssbd2020.ssbd04.security;


import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import pl.lodz.p.it.ssbd2020.ssbd04.common.Config;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.security.enterprise.identitystore.CredentialValidationResult;
import java.util.Arrays;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * Zajmuje się tworzeniem i parsowaniem JWT.
 */
@RequestScoped
public class JWTProvider {
    private static final String AUTHORITIES_SECTION = "auth";

    private long tokenValidity;

    @Inject
    private Config config;

    @PostConstruct
    private void init() {
        this.tokenValidity = TimeUnit.MINUTES.toMillis(config.getJWTValidity());
    }

    /**
     * Tworzy nowy JWT na podstawie danych klienta po poprawnej autoryzacji.
     * Czas ważności tokenu i klucz, którym jest podpisywany pochodzi z obiektu zawierającego konfigurację aplikacji.
     *
     * @param result
     * @return
     */
    public JWT create(CredentialValidationResult result) {
        String principal = result.getCallerPrincipal().getName();
        Set<String> authorities = result.getCallerGroups();
        String base64 = Jwts.builder()
                .setSubject(principal)
                .claim(AUTHORITIES_SECTION, String.join(",", authorities))
                .signWith(SignatureAlgorithm.HS512, config.getJWTSecretKey())
                .setExpiration(new Date((new Date()).getTime() + tokenValidity))
                .compact();

        return new JWT(principal, authorities, base64);
    }

    /**
     * Odświeża JWT, jeżeli czas ważności tokenu upływa za mniej niż połowę bazowego czasu.
     *
     * @param token
     * @return
     */
    public JWT refresh(String token) {
        Claims claims = Jwts.parser()
                .setSigningKey(config.getJWTSecretKey())
                .parseClaimsJws(token)
                .getBody();

        String base64 = Jwts.builder()
                .setClaims(claims)
                .signWith(SignatureAlgorithm.HS512, config.getJWTSecretKey())
                .setExpiration(new Date((new Date()).getTime() + tokenValidity))
                .compact();

        String authorities = (String) claims.get(AUTHORITIES_SECTION);
        return new JWT(claims.getSubject(), Set.of(authorities.split(",")), base64);
    }


    /**
     * Parsuje, weryfikuje podpis i tworzy obiekt reprezentujący JWT.
     *
     * @param token JWT w formacie base64
     * @return obiekt JWT
     */
    public JWT load(String token) {
        Claims claims = Jwts.parser()
                .setSigningKey(config.getJWTSecretKey())
                .parseClaimsJws(token)
                .getBody();

        String principal = claims.getSubject();
        Set<String> authorities = new HashSet<>(Arrays.asList(claims.get(AUTHORITIES_SECTION).toString().split(",")));

        return new JWT(principal, authorities, token);
    }
}

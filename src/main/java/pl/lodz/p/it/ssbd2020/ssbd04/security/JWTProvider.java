package pl.lodz.p.it.ssbd2020.ssbd04.security;


import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import pl.lodz.p.it.ssbd2020.ssbd04.utils.Config;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.security.enterprise.identitystore.CredentialValidationResult;
import java.util.Arrays;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.TimeUnit;

@RequestScoped
public class JWTProvider {
    private static final String AUTHORITIES_SECTION = "auth";

    private long tokenValidity;

    @Inject
    private Config config;

    @PostConstruct
    private void init() {
        this.tokenValidity = TimeUnit.MINUTES.toMillis(config.getLong(config.JWT_VALIDITY_KEY));
    }

    public JWT create(CredentialValidationResult result) {
        String principal = result.getCallerPrincipal().getName();
        Set<String> authorities = result.getCallerGroups();
        String base64 = Jwts.builder()
                .setSubject(principal)
                .claim(AUTHORITIES_SECTION, String.join(",", authorities))
                .signWith(SignatureAlgorithm.HS512, config.get(config.JWT_SECRET_KEY))
                .setExpiration(new Date((new Date()).getTime() + tokenValidity))
                .compact();

        return new JWT(principal, authorities, base64);
    }

    public JWT load(String token) {
        Claims claims = Jwts.parser()
                .setSigningKey(config.get(config.JWT_SECRET_KEY))
                .parseClaimsJws(token)
                .getBody();

        String principal = claims.getSubject();
        Set<String> authorities = new HashSet<>(Arrays.asList(claims.get(AUTHORITIES_SECTION).toString().split(",")));

        return new JWT(principal, authorities, token);
    }
}

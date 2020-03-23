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
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@RequestScoped
public class JWTProvider {
    private static final String AUTHORITIES_SECTION = "auth";
    private static final String JWT_SECRET_KEY = "jwt.secretKey";
    private static final String JWT_VALIDITY_KEY = "jwt.validity";

    private long tokenValidity;

    @Inject
    private Config config;

    @PostConstruct
    private void init() {
        this.tokenValidity = TimeUnit.MINUTES.toMillis(config.getLong(JWT_VALIDITY_KEY));
    }

    public JWT create(CredentialValidationResult result) {
        String principal = result.getCallerPrincipal().getName();
        Set<String> authorities = result.getCallerGroups();
        String base64 = Jwts.builder()
                .setSubject(principal)
                .claim(AUTHORITIES_SECTION, String.join(",", authorities))
                .signWith(SignatureAlgorithm.HS512, config.get(JWT_SECRET_KEY))
                .setExpiration(new Date((new Date()).getTime() + tokenValidity))
                .compact();

        return new JWT(principal, authorities, base64);
    }

    public JWT load(String token) {
        Claims claims = Jwts.parser()
                .setSigningKey(config.get(JWT_SECRET_KEY))
                .parseClaimsJws(token)
                .getBody();

        String principal = claims.getSubject();
        Set<String> authorities = Arrays.stream(claims.get(AUTHORITIES_SECTION).toString().split(","))
                .collect(Collectors.toSet());

        return new JWT(principal, authorities, token);
    }
}

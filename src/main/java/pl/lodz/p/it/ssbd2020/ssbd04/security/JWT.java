package pl.lodz.p.it.ssbd2020.ssbd04.security;

import java.util.Set;

/**
 * Służy do przechowywania danych uzyskanych po uwierzytelnieniu i autoryzacji.
 * Obiekty tej klasy są deserializowane i wysyłane klientowi.
 */
public class JWT {
    private final String principal;
    private final Set<String> authorities;
    private final String token;

    public JWT(String principal, Set<String> authorities, String token) {
        this.principal = principal;
        this.authorities = authorities;
        this.token = token;
    }

    public String getPrincipal() {
        return principal;
    }

    public Set<String> getAuthorities() {
        return authorities;
    }

    public String getToken() {
        return token;
    }
}

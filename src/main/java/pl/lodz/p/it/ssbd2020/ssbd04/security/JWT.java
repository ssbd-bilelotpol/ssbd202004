package pl.lodz.p.it.ssbd2020.ssbd04.security;

import java.util.Set;

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

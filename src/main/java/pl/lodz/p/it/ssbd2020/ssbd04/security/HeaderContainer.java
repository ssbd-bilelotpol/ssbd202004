package pl.lodz.p.it.ssbd2020.ssbd04.security;

import javax.enterprise.context.RequestScoped;
import javax.ws.rs.core.HttpHeaders;

@RequestScoped
public class HeaderContainer {
    private HttpHeaders headers;


    public HttpHeaders getHeaders() {
        return headers;
    }

    public void setHeaders(HttpHeaders headers) {
        this.headers = headers;
    }
}

package pl.lodz.p.it.ssbd2020.ssbd04.utils;


import javax.enterprise.context.ApplicationScoped;
import java.io.Serializable;

@ApplicationScoped
public class Config implements Serializable {
    public final String FRONTEND_URL = "FRONTEND_URL";
    public final String JWT_SECRET_KEY = "JWT_SECRET_KEY";
    public final String JWT_VALIDITY_KEY = "JWT_VALIDITY";

    public String get(String key) {
        return System.getenv(key);
    }

    public long getLong(String key) {
        return Long.parseLong(get(key));
    }
}

package pl.lodz.p.it.ssbd2020.ssbd04.utils;


import javax.enterprise.context.ApplicationScoped;
import java.io.Serializable;
import java.util.Properties;

@ApplicationScoped
public class Config implements Serializable {

    private Properties p = new Properties();

    public Config() {
        p.setProperty("jwt.secretKey", "alamakota");
        p.setProperty("jwt.validity", "15");
    }

    public String get(String key) {
        return p.getProperty(key);
    }

    public long getLong(String key) {
        return Long.parseLong(p.getProperty(key));
    }
}

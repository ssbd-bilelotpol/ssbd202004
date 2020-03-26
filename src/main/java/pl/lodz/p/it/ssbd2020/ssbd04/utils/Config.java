package pl.lodz.p.it.ssbd2020.ssbd04.utils;


import pl.lodz.p.it.ssbd2020.ssbd04.security.JWTAuthenticationMechanism;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.util.Properties;
import java.util.logging.Logger;

import static java.util.logging.Level.INFO;

@ApplicationScoped
public class Config implements Serializable {
    private final String FRONTEND_URL = "frontend.url";
    private final String JWT_SECRET_KEY = "jwt.secretKey";
    private final String JWT_VALIDITY_KEY = "jwt.validity";
    private static final String CONFIG_FILE = "config.properties";

    private Properties properties = new Properties();

    @PostConstruct
    private void init() {
        try {
            InputStream inputStream = getClass().getClassLoader().getResourceAsStream(CONFIG_FILE);
            if (inputStream == null) {
                throw new FileNotFoundException("Couldn't find file: " + CONFIG_FILE);
            }

            properties.load(inputStream);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public String get(String key) {
        return properties.getProperty(key);
    }

    public String getFrontendURL() {
        return get(FRONTEND_URL);
    }

    public String getJWTSecretKey() {
        return get(JWT_SECRET_KEY);
    }

    public Long getJWTValidity() {
        return getLong(JWT_VALIDITY_KEY);
    }

    public long getLong(String key) {
        return Long.parseLong(get(key));
    }
}

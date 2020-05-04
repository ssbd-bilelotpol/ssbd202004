package pl.lodz.p.it.ssbd2020.ssbd04.security;

import javax.ws.rs.NameBinding;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Odpowiada za adnotacje wiążącą filtr EtagFilter z metodami
 */
@NameBinding
@Retention(RetentionPolicy.RUNTIME)
public @interface EtagBinding {
}

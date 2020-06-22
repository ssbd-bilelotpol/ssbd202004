package pl.lodz.p.it.ssbd2020.ssbd04.validation;

import javax.validation.Constraint;
import javax.validation.Payload;
import javax.validation.constraints.Pattern;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Weryfikuje kod kraju
 */
@Target({ElementType.FIELD, ElementType.PARAMETER})
@Constraint(validatedBy = AirportCountryValidator.class)
@Retention(RetentionPolicy.RUNTIME)
@Pattern(regexp = Regex.AIRPORT_COUNTRY)
public @interface AirportCountry {
    String message() default "{validation.error.airport_country}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}

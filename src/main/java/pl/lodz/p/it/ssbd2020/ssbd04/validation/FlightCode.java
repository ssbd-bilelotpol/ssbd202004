package pl.lodz.p.it.ssbd2020.ssbd04.validation;

import javax.validation.Constraint;
import javax.validation.Payload;
import javax.validation.constraints.Pattern;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * Służy do weryfikacji FlightCode przez BeanValidation.
 */
@Target({ElementType.FIELD, ElementType.PARAMETER})
@Constraint(validatedBy = {})
@Retention(RUNTIME)
@Pattern(regexp = Regex.FLIGHT_CODE, message = "{validation.error.flightCode}")
public @interface FlightCode {
    String message() default "{validation.error.flightCode}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
package pl.lodz.p.it.ssbd2020.ssbd04.validation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

/**
 * Sprawdza poprawność liczby pasażerów
 */
@Documented
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = {PassengerCountValidator.class})
public @interface PassengerCount {
    String message() default "{validation.error.passengerCount}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}

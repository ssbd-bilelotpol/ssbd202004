package pl.lodz.p.it.ssbd2020.ssbd04.validation;

import javax.validation.Constraint;
import javax.validation.Payload;
import javax.validation.constraints.Size;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * Weryfikuje hasło
 */
@Target({ElementType.FIELD, ElementType.PARAMETER})
@Constraint(validatedBy = {})
@Retention(RUNTIME)
@Size(min = 8, max = 64)
public @interface Password {
    String message() default "{validation.error.password}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
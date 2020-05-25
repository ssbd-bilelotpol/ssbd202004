package pl.lodz.p.it.ssbd2020.ssbd04.validation;

import javax.validation.Constraint;
import javax.validation.Payload;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * Służy do weryfikacji UUID przez BeanValidation.
 */
@Target({ElementType.FIELD, ElementType.PARAMETER})
@Constraint(validatedBy = {})
@Retention(RUNTIME)
@Size(min = 1, max = 30)
@Pattern(regexp = Regex.LAST_NAME, message = "{validation.error.lastName}")
public @interface LastName {
    String message() default "{validation.error.lastName}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
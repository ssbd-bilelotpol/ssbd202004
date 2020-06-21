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
 * Służy do weryfikacji numer telefonu przez BeanValidation.
 */
@Target({ElementType.FIELD, ElementType.PARAMETER})
@Constraint(validatedBy = {})
@Retention(RUNTIME)
@Size(min = 9, max = 15)
@Pattern(regexp = Regex.PHONE, message = "{validation.error.phone}")
public @interface Phone {
    String message() default "{validation.error.phone}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
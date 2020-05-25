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
@Size(min = 3, max = 30)
@Pattern(regexp = Regex.LOGIN, message = "{validation.error.login}")
public @interface Login {
    String message() default "{validation.error.loginName}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
package pl.lodz.p.it.ssbd2020.ssbd04.validation;

import javax.validation.Constraint;
import javax.validation.Payload;
import javax.validation.constraints.Pattern;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Weryfikuje nazwę korzyści
 */
@Target({ElementType.FIELD, ElementType.PARAMETER})
@Constraint(validatedBy = {})
@Retention(RetentionPolicy.RUNTIME)
@Pattern(regexp = Regex.BENEFIT_NAME)
public @interface BenefitName {
    String message() default "{validation.error.benefit_name}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}

package pl.lodz.p.it.ssbd2020.ssbd04.validation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * Weryfikuje kolejność dat w obiekcie
 */
@Target({ElementType.TYPE})
@Retention(RUNTIME)
@Constraint(validatedBy = DateOrderValidator.class)
@Documented
public @interface DateOrder {
    String message() default "{validation.error.dateOrder}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    String before();

    String after();
}

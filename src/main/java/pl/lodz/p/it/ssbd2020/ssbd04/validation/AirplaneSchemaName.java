package pl.lodz.p.it.ssbd2020.ssbd04.validation;

import javax.validation.Constraint;
import javax.validation.Payload;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.FIELD, ElementType.PARAMETER})
@Constraint(validatedBy = {})
@Retention(RetentionPolicy.RUNTIME)
@Pattern(regexp = Regex.AIRPLANE_SCHEMA_NAME)
@Size(min = 5, max = 60)
public @interface AirplaneSchemaName {
    String message() default "{validation.error.airplane_schema_name}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}

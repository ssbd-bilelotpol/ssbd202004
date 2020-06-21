package pl.lodz.p.it.ssbd2020.ssbd04.validation;

import javax.validation.Constraint;
import javax.validation.Payload;
import javax.validation.constraints.Pattern;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Sprawdza, czy listy pustych kolumn/wierszy zawierają poprawne wartości.
 */
@Target({ElementType.FIELD, ElementType.PARAMETER})
@Constraint(validatedBy = {})
@Retention(RetentionPolicy.RUNTIME)
@Pattern(regexp = Regex.AIRPLANE_SCHEMA_ROW_COL)
public @interface AirplaneSchemaRowCol {
    String message() default "{validation.error.airplane_empty_row_col_error}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}

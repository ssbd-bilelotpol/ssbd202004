package pl.lodz.p.it.ssbd2020.ssbd04.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.lang.reflect.Field;
import java.time.LocalDateTime;

/**
 * Klasa wykorzystywana przez interfejs znacznikowy walidatora kolejności dat w obiekcie
 */
public class DateOrderValidator implements ConstraintValidator<DateOrder, Object> {
    private String fieldBefore;
    private String fieldAfter;

    @Override
    public boolean isValid(Object value, ConstraintValidatorContext context) {
        try {
            LocalDateTime first = getDate(fieldBefore, value);
            LocalDateTime second = getDate(fieldAfter, value);
            if (first.isBefore(second))
                return true;
        } catch (Exception e) {
            return false;
        }
        return false;
    }

    @Override
    public void initialize(DateOrder constraintAnnotation) {
        this.fieldBefore = constraintAnnotation.before();
        this.fieldAfter = constraintAnnotation.after();
    }

    private LocalDateTime getDate(String fieldName, Object object) throws NoSuchFieldException, IllegalAccessException {
        Field field = object.getClass().getDeclaredField(fieldName);
        field.setAccessible(true);
        return (LocalDateTime) field.get(object);
    }
}

package pl.lodz.p.it.ssbd2020.ssbd04.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Locale;

public class AirportCountryValidator implements ConstraintValidator<AirportCountry, String> {

    @Override
    public void initialize(AirportCountry constraintAnnotation) {

    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        return Locale.getISOCountries(Locale.IsoCountryCode.PART1_ALPHA2).contains(value);
    }
}

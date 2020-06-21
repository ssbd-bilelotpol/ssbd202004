package pl.lodz.p.it.ssbd2020.ssbd04.validation;

import pl.lodz.p.it.ssbd2020.ssbd04.mob.dto.TicketBuyDto;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * Sprawdza poprawność liczby pasażerów
 */
public class PassengerCountValidator implements ConstraintValidator<PassengerCount, TicketBuyDto> {

    @Override
    public boolean isValid(TicketBuyDto dto, ConstraintValidatorContext constraintValidatorContext) {
        if (dto.getPassengers() == null || dto.getDestinationFlight().getSeats() == null) {
            return false;
        }

        return dto.getPassengers().size() == dto.getDestinationFlight().getSeats().size()
                && (dto.getReturnFlight() == null || dto.getPassengers().size() == dto.getReturnFlight().getSeats().size());
    }

}
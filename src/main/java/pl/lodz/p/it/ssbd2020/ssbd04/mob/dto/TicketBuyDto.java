package pl.lodz.p.it.ssbd2020.ssbd04.mob.dto;

import pl.lodz.p.it.ssbd2020.ssbd04.validation.PassengerCount;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

/**
 * Reprezentuje żądanie zakupu biletu
 */
@PassengerCount
public class TicketBuyDto {

    @NotNull
    private SelectedFlightDto destinationFlight;
    private SelectedFlightDto returnFlight;

    @NotNull
    @Size(min = 1, max = 25)
    private List<CreatePassengerDto> passengers;

    public TicketBuyDto() {
    }

    public List<CreatePassengerDto> getPassengers() {
        return passengers;
    }

    public void setPassengers(List<CreatePassengerDto> passengers) {
        this.passengers = passengers;
    }

    public SelectedFlightDto getDestinationFlight() {
        return destinationFlight;
    }

    public void setDestinationFlight(SelectedFlightDto destinationFlight) {
        this.destinationFlight = destinationFlight;
    }

    public SelectedFlightDto getReturnFlight() {
        return returnFlight;
    }

    public void setReturnFlight(SelectedFlightDto returnFlight) {
        this.returnFlight = returnFlight;
    }

}

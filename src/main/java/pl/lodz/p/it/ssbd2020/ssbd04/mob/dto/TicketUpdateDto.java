package pl.lodz.p.it.ssbd2020.ssbd04.mob.dto;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * Reprezentuje żądanie aktualizacji biletu
 */
public class TicketUpdateDto {

    @NotNull
    private Long ticketId;

    @NotNull
    private List<UpdatePassengerDto> passengers;

    public TicketUpdateDto() {
    }

    public List<UpdatePassengerDto> getPassengers() {
        return passengers;
    }

    public void setPassengers(List<UpdatePassengerDto> passengers) {
        this.passengers = passengers;
    }

    public Long getTicketId() {
        return ticketId;
    }

    public void setTicketId(Long ticketId) {
        this.ticketId = ticketId;
    }

}

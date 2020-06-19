package pl.lodz.p.it.ssbd2020.ssbd04.mob.dto;

import javax.validation.constraints.NotNull;

public class TicketReturnDto {

    @NotNull
    private Long ticketId;

    public Long getTicketId() {
        return ticketId;
    }

    public void setTicketId(Long ticketId) {
        this.ticketId = ticketId;
    }

}

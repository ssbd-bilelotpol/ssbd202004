package pl.lodz.p.it.ssbd2020.ssbd04.mob.dto;

import pl.lodz.p.it.ssbd2020.ssbd04.entities.Ticket;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * Reprezentuje listę biletów
 */
public class TicketListDto {

    private Long id;
    private String flightCode;
    private String email;
    private BigDecimal totalPrice;
    private LocalDateTime date;

    public TicketListDto() {
    }

    public TicketListDto(Ticket ticket) {
        this.id = ticket.getId();
        this.flightCode = ticket.getFlight().getFlightCode();
        this.email = ticket.getAccount().getAccountDetails().getEmail();
        this.totalPrice = ticket.getTotalPrice();
        this.date = ticket.getCreationDateTime();
    }

    public String getFlightCode() {
        return flightCode;
    }

    public String getEmail() {
        return email;
    }

    public BigDecimal getTotalPrice() {
        return totalPrice;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public Long getId() {
        return id;
    }
}

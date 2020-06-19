package pl.lodz.p.it.ssbd2020.ssbd04.mob.dto;

import pl.lodz.p.it.ssbd2020.ssbd04.entities.Ticket;

import java.math.BigDecimal;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Reprezentuje bilet
 */
public class TicketDto {

    private Long id;
    private FlightDto flight;
    private BigDecimal totalPrice;
    private Set<PassengerDto> passengers;

    public TicketDto(Ticket ticket) {
        this.id = ticket.getId();
        this.flight = new FlightDto(ticket.getFlight());
        this.totalPrice = ticket.getTotalPrice();
        this.passengers = ticket.getPassengers()
                .stream()
                .map(PassengerDto::new)
                .collect(Collectors.toSet());
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public FlightDto getFlight() {
        return flight;
    }

    public void setFlight(FlightDto flight) {
        this.flight = flight;
    }

    public BigDecimal getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(BigDecimal totalPrice) {
        this.totalPrice = totalPrice;
    }

    public Set<PassengerDto> getPassengers() {
        return passengers;
    }

    public void setPassengers(Set<PassengerDto> passengers) {
        this.passengers = passengers;
    }

}

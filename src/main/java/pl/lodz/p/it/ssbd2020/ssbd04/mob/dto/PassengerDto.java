package pl.lodz.p.it.ssbd2020.ssbd04.mob.dto;

import pl.lodz.p.it.ssbd2020.ssbd04.entities.Passenger;
import pl.lodz.p.it.ssbd2020.ssbd04.security.Signable;

public class PassengerDto implements Signable {

    private Long id;
    private String email;
    private String firstName;
    private String lastName;
    private String phoneNumber;
    private SeatDto seat;
    private FlightDto flight;
    private Long ticketId;

    public PassengerDto(Passenger passenger) {
        this.id = passenger.getId();
        this.email = passenger.getEmail();
        this.firstName = passenger.getFirstName();
        this.lastName = passenger.getLastName();
        this.phoneNumber = passenger.getPhoneNumber();
        this.seat = new SeatDto(passenger.getSeat());
        this.flight = new FlightDto(passenger.getFlight());
        this.ticketId = passenger.getTicket().getId();
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public SeatDto getSeat() {
        return seat;
    }

    public void setSeat(SeatDto seat) {
        this.seat = seat;
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

    public Long getTicketId() {
        return ticketId;
    }

    public void setTicketId(Long ticketId) {
        this.ticketId = ticketId;
    }

    @Override
    public String createMessage() {
        return String.format("%d.%s.%d", this.id, this.flight.getCode(), this.ticketId);
    }

}

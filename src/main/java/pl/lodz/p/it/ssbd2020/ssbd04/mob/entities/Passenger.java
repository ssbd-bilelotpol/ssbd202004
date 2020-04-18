package pl.lodz.p.it.ssbd2020.ssbd04.mob.entities;


import pl.lodz.p.it.ssbd2020.ssbd04.mol.entities.Seat;
import pl.lodz.p.it.ssbd2020.ssbd04.utils.AbstractEntity;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.Objects;

/**
 * Klasa encyjna zawierająca informacje o szczegółach pasażera
 */
@Entity
@Table(indexes = {
        @Index(name = "passenger_seat_fk", columnList = "seat_id"),
        @Index(name = "passenger_ticket_fk", columnList = "ticket_id")
})
public class Passenger extends AbstractEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "passenger_generator")
    @SequenceGenerator(name = "passenger_generator", sequenceName = "passenger_seq", allocationSize = 10)
    @Column(updatable = false)
    private Long id;

    @NotNull
    @Size(min = 1, max = 30)
    @Column(nullable = false, length = 30, name = "first_name")
    private String firstName;

    @NotNull
    @Size(min = 1, max = 30)
    @Column(nullable = false, length = 30, name = "last_name")
    private String lastName;

    @NotNull
    @Size(min = 3, max = 255)
    @Email
    @Column(updatable = false, nullable = false)
    private String email;

    @NotNull
    @Size(min = 9, max = 15)
    @Column(nullable = false, length = 15, name = "phone_number")
    private String phoneNumber;

    @NotNull
    @ManyToOne(cascade = {CascadeType.REFRESH})
    @JoinColumn(name = "seat_id", nullable = false, foreignKey = @ForeignKey(name = "passenger_seat_fk"))
    private Seat seat;

    @NotNull
    @ManyToOne(cascade = {CascadeType.REFRESH})
    @JoinColumn(name = "ticket_id", nullable = false, foreignKey = @ForeignKey(name = "passenger_ticket_fk"))
    private Ticket ticket;

    public Passenger() {
    }

    public Passenger(@NotNull @Size(min = 1, max = 30) String firstName, @NotNull @Size(min = 1, max = 30) String lastName,
                     @NotNull @Size(min = 1, max = 255) @Email String email, @NotNull @Size(min = 1, max = 50) String phoneNumber,
                     Seat seat) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.seat = seat;
    }

    public Long getId() {
        return id;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public Seat getSeat() {
        return seat;
    }

    public void setSeat(Seat seat) {
        this.seat = seat;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Passenger)) return false;
        Passenger passenger = (Passenger) o;
        return firstName.equals(passenger.firstName) &&
                lastName.equals(passenger.lastName) &&
                email.equals(passenger.email) &&
                phoneNumber.equals(passenger.phoneNumber) &&
                seat.equals(passenger.seat) &&
                ticket.equals(passenger.ticket);
    }

    @Override
    public int hashCode() {
        return Objects.hash(firstName, lastName, email, phoneNumber, seat, ticket);
    }
}

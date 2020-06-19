package pl.lodz.p.it.ssbd2020.ssbd04.entities;

import pl.lodz.p.it.ssbd2020.ssbd04.common.AbstractEntity;

import javax.persistence.*;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * Klasa encyjna odpowiedzialna za przechowywanie informacji o sprzedaży biletu
 */
@NamedQueries({
        @NamedQuery(name = "Ticket.findByFlight", query = "SELECT ticket from Ticket ticket WHERE ticket.flight.id = :flightId"),
        @NamedQuery(name = "Ticket.findByAccount", query = "SELECT ticket from Ticket ticket WHERE ticket.account.id = :accountId"),
        @NamedQuery(name = "Ticket.findById", query = "SELECT ticket from Ticket ticket WHERE ticket.id = :id"),
})
@Entity
@Table(
        indexes = {
                @Index(name = "ticket_flight_fk", columnList = "flight_id"),
                @Index(name = "ticket_account_fk", columnList = "account_id")
        }
)
public class Ticket extends AbstractEntity implements Serializable {
    public static final String CONSTRAINT_SEAT_TAKEN = "passenger_seat_taken";

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ticket_generator")
    @SequenceGenerator(name = "ticket_generator", sequenceName = "ticket_seq", allocationSize = 10)
    @Column(updatable = false)
    private Long id;

    @NotNull
    @ManyToOne(cascade = {CascadeType.REFRESH})
    @JoinColumn(name = "flight_id", nullable = false, updatable = false, foreignKey = @ForeignKey(name = "ticket_flight_fk"))
    private Flight flight;

    @Digits(integer = 7, fraction = 2)
    @NotNull
    @Column(precision = 7, scale = 2, nullable = false, name = "total_price")
    private BigDecimal totalPrice;

    @NotNull
    @ManyToOne(cascade = {CascadeType.REFRESH})
    @JoinColumn(name = "account_id", nullable = false, updatable = false, foreignKey = @ForeignKey(name = "ticket_account_fk"))
    private Account account;

    @Column(nullable = false)
    @OneToMany(cascade = {CascadeType.REMOVE, CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH},
            orphanRemoval = true,
            mappedBy = "ticket")
    private Set<Passenger> passengers = new HashSet<>();

    public Ticket() {
    }

    public Ticket(Flight flight, @Digits(integer = 7, fraction = 2) @NotNull BigDecimal totalPrice, Account account,
                  Set<Passenger> passenger) {
        this.flight = flight;
        this.totalPrice = totalPrice;
        this.account = account;
        this.passengers = passenger;
    }

    public Long getId() {
        return id;
    }

    public Flight getFlight() {
        return flight;
    }

    public void setFlight(Flight flight) {
        this.flight = flight;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public BigDecimal getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(BigDecimal totalPrice) {
        this.totalPrice = totalPrice;
    }

    public Set<Passenger> getPassengers() {
        return passengers;
    }

    public void setPassengers(Set<Passenger> passengers) {
        this.passengers = passengers;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Ticket)) return false;
        Ticket ticket = (Ticket) o;
        return flight.equals(ticket.flight) &&
                totalPrice.equals(ticket.totalPrice) &&
                account.equals(ticket.account) &&
                passengers.equals(ticket.passengers);
    }

    @Override
    public int hashCode() {
        return Objects.hash(flight, totalPrice, account, passengers);
    }

    @Override
    public String toString() {
        return "Ticket{" +
                "id=" + id +
                ", version=" + getVersion() +
                "}";
    }
}

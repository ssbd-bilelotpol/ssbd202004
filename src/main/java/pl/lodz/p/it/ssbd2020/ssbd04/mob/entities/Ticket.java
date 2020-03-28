package pl.lodz.p.it.ssbd2020.ssbd04.mob.entities;

import pl.lodz.p.it.ssbd2020.ssbd04.mok.entities.Account;
import pl.lodz.p.it.ssbd2020.ssbd04.mok.entities.AccountDetails;
import pl.lodz.p.it.ssbd2020.ssbd04.mol.entities.Flight;

import javax.persistence.*;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Set;

/**
 * Klasa encyjna odpowiedzialna za przechowywanie informacji o sprzedaży biletu
 */
@Entity
public class Ticket implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ticket_generator")
    @SequenceGenerator(name = "ticket_generator", sequenceName = "ticket_seq", allocationSize = 10)
    @Column(updatable = false)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "flight_id", nullable = false, updatable = false)
    private Flight flight;

    @Digits(integer = 7, fraction = 2)
    @NotNull
    @Column(precision = 7, scale = 2, nullable = false)
    private BigDecimal totalPrice;

    @ManyToMany
    @JoinTable(
            name = "ticket_seats",
            joinColumns = @JoinColumn(name = "ticket_id"),
            inverseJoinColumns = @JoinColumn(name = "seat_id"),
            uniqueConstraints = @UniqueConstraint(
                    columnNames = {"ticket_id", "seat_id"}
            )
    )
    private Set<Seat> seats;

    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE})
    @JoinTable(
            name = "ticket_account_details",
            joinColumns = @JoinColumn(name = "ticket_id"),
            inverseJoinColumns = @JoinColumn(name = "account_details_id"),
            uniqueConstraints = @UniqueConstraint(
                    columnNames = {"ticket_id", "account_details_id"}
            )
    )
    private Set<AccountDetails> accountDetails;

    @ManyToOne
    @JoinColumn(name = "account_id", nullable = false)
    private Account account;

    @Version
    private Long version;

    public Ticket() {
    }

    public Ticket(Flight flight, @Digits(integer = 7, fraction = 2) @NotNull BigDecimal totalPrice, Set<Seat> seats,
                  Set<AccountDetails> accountDetails, Account account) {
        this.flight = flight;
        this.totalPrice = totalPrice;
        this.seats = seats;
        this.accountDetails = accountDetails;
        this.account = account;
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

    public Set<AccountDetails> getAccountDetails() {
        return accountDetails;
    }

    public void setAccountDetails(Set<AccountDetails> accountDetails) {
        this.accountDetails = accountDetails;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public Set<Seat> getSeats() {
        return seats;
    }

    public void setSeats(Set<Seat> seats) {
        this.seats = seats;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Ticket)) return false;

        Ticket ticket = (Ticket) o;

        if (!flight.equals(ticket.flight)) return false;
        if (!seats.equals(ticket.seats)) return false;
        return account.equals(ticket.account);
    }

    @Override
    public int hashCode() {
        int result = flight.hashCode();
        result = 31 * result + seats.hashCode();
        result = 31 * result + account.hashCode();
        return result;
    }
}

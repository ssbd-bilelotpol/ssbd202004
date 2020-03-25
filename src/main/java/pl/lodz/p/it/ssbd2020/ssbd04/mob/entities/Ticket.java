package pl.lodz.p.it.ssbd2020.ssbd04.mob.entities;

import pl.lodz.p.it.ssbd2020.ssbd04.mok.entities.Account;
import pl.lodz.p.it.ssbd2020.ssbd04.mok.entities.AccountDetails;
import pl.lodz.p.it.ssbd2020.ssbd04.mol.entities.Flight;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Set;

/**
 * Klasa encyjna odpowiedzialna za przechowywanie informacji o sprzedaży biletu
 */
@Entity
public class Ticket implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(updatable = false)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "flight_id", nullable = false)
    private Flight flight;

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

    @ManyToMany
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
    @JoinColumn(name = "account_id", referencedColumnName = "id", nullable = false)
    private Account account;

    @Version
    private Long version;

    public Ticket() {
    }

    public Ticket(Flight flight, Set<Seat> seats, Set<AccountDetails> accountDetails, Account account, Long version) {
        this.flight = flight;
        this.seats = seats;
        this.accountDetails = accountDetails;
        this.account = account;
        this.version = version;
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

    public Long getVersion() {
        return version;
    }

    public void setVersion(Long version) {
        this.version = version;
    }

    public Set<Seat> getSeats() {
        return seats;
    }

    public void setSeats(Set<Seat> seats) {
        this.seats = seats;
    }
}

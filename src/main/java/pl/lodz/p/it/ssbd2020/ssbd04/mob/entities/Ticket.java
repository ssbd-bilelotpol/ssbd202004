package pl.lodz.p.it.ssbd2020.ssbd04.mob.entities;

import pl.lodz.p.it.ssbd2020.ssbd04.mok.entities.Account;
import pl.lodz.p.it.ssbd2020.ssbd04.mol.entities.Flight;

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
@Entity
public class Ticket implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ticket_generator")
    @SequenceGenerator(name = "ticket_generator", sequenceName = "ticket_seq", allocationSize = 10)
    @Column(updatable = false)
    private Long id;

    @ManyToOne(cascade = {CascadeType.REFRESH})
    @JoinColumn(name = "flight_id", nullable = false, updatable = false)
    private Flight flight;

    @Digits(integer = 7, fraction = 2)
    @NotNull
    @Column(precision = 7, scale = 2, nullable = false, name = "total_price")
    private BigDecimal totalPrice;

    @ManyToOne(cascade = {CascadeType.REFRESH})
    @JoinColumn(name = "account_id", nullable = false)
    private Account account;

    @Column(nullable = false)
    @OneToMany(cascade = {CascadeType.REMOVE, CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH},
            orphanRemoval = true,
            mappedBy = "ticket")
    private Set<Passenger> passenger = new HashSet<>();

    @Version
    private Long version;

    public Ticket() {
    }

    public Ticket(Flight flight, @Digits(integer = 7, fraction = 2) @NotNull BigDecimal totalPrice, Account account,
                  Set<Passenger> passenger) {
        this.flight = flight;
        this.totalPrice = totalPrice;
        this.account = account;
        this.passenger = passenger;
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

    public Set<Passenger> getPassenger() {
        return passenger;
    }

    public void setPassenger(Set<Passenger> passenger) {
        this.passenger = passenger;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Ticket)) return false;

        Ticket ticket = (Ticket) o;

        if (!Objects.equals(flight, ticket.flight)) return false;
        if (!Objects.equals(totalPrice, ticket.totalPrice)) return false;
        if (!Objects.equals(account, ticket.account)) return false;
        return Objects.equals(passenger, ticket.passenger);
    }

    @Override
    public int hashCode() {
        int result = flight != null ? flight.hashCode() : 0;
        result = 31 * result + (totalPrice != null ? totalPrice.hashCode() : 0);
        result = 31 * result + (account != null ? account.hashCode() : 0);
        result = 31 * result + (passenger != null ? passenger.hashCode() : 0);
        return result;
    }
}

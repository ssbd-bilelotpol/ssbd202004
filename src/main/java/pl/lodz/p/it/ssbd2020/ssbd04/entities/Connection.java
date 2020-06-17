package pl.lodz.p.it.ssbd2020.ssbd04.entities;

import pl.lodz.p.it.ssbd2020.ssbd04.common.AbstractEntity;

import javax.persistence.*;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;

/**
 * Informacje o połączeniu między dwoma miejscami, lotach, które te połączenie obsługują, lotnisku, z którego
 * te loty odlatują oraz bazowej cenie połączenia
 */

@NamedQueries({
        @NamedQuery(name = "Connection.findBetween",
                query = "SELECT connection from Connection connection WHERE UPPER(connection.source.code) LIKE UPPER(CONCAT('%', :sourceCode, '%')) AND UPPER(connection.destination.code) LIKE UPPER(CONCAT('%', :destinationCode, '%'))"),
        @NamedQuery(name = "Connection.findByDestination",
                query = "SELECT connection from Connection connection WHERE UPPER(connection.destination.code) LIKE UPPER(CONCAT('%', :destinationCode, '%'))"),
        @NamedQuery(name = "Connection.findBySource",
                query = "SELECT connection from Connection connection WHERE UPPER(connection.source.code) LIKE UPPER(CONCAT('%', :sourceCode, '%'))"),
        @NamedQuery(name = "Connection.findByPhrase",
                query = "SELECT connection from Connection connection WHERE " +
                        "UPPER(CONCAT(connection.source.code, ' - ', connection.destination.code)) LIKE UPPER(CONCAT(:phrase, '%'))")
})

@Entity
@Table(
        indexes = {
                @Index(name = "connection_airport_dst_fk", columnList = "destination_id"),
                @Index(name = "connection_airport_src_fk", columnList = "source_id")
        }
)
@SecondaryTable(name = "connection_stats", pkJoinColumns = @PrimaryKeyJoinColumn(name = "connection_id", referencedColumnName = "id"))
public class Connection extends AbstractEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false)
    private Long id;

    @NotNull
    @ManyToOne(cascade = {CascadeType.REFRESH})
    @JoinColumn(name = "destination_id", nullable = false, updatable = false, foreignKey = @ForeignKey(name = "connection_airport_dst_fk"))
    private Airport destination;

    @NotNull
    @ManyToOne(cascade = {CascadeType.REFRESH})
    @JoinColumn(name = "source_id", nullable = false, updatable = false, foreignKey = @ForeignKey(name = "connection_airport_src_fk"))
    private Airport source;

    @DecimalMin("0.0")
    @Digits(integer = 7, fraction = 2)
    @NotNull
    @Column(precision = 7, scale = 2, nullable = false, name = "base_price")
    private BigDecimal basePrice;

    @Digits(integer = 7, fraction = 2)
    @Column(table = "connection_stats", name = "profit", precision = 7, scale = 2)
    private BigDecimal profit;

    public Connection() {
    }

    public Connection(@Digits(integer = 7, fraction = 2) @NotNull BigDecimal basePrice) {
        this.basePrice = basePrice;
    }

    public Airport getDestination() {
        return destination;
    }

    public void setDestination(Airport destination) {
        this.destination = destination;
    }

    public Airport getSource() {
        return source;
    }

    public void setSource(Airport source) {
        this.source = source;
    }

    public void setBasePrice(BigDecimal basePrice) {
        this.basePrice = basePrice;
    }

    public BigDecimal getBasePrice() {
        return basePrice;
    }

    public BigDecimal getProfit() {
        return profit;
    }

    public void setProfit(BigDecimal profit) {
        this.profit = profit;
    }

    public Long getId() {
        return id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Connection)) return false;
        Connection that = (Connection) o;
        return destination.equals(that.destination) &&
                source.equals(that.source) &&
                basePrice.equals(that.basePrice);
    }

    @Override
    public int hashCode() {
        return Objects.hash(destination, source, basePrice);
    }

    @Override
    public String toString() {
        return "Connection{" +
                "id=" + id +
                ", version=" + getVersion() +
                "}";
    }
}

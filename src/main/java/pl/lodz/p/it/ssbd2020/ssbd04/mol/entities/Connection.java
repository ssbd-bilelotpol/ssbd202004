package pl.lodz.p.it.ssbd2020.ssbd04.mol.entities;

import javax.persistence.*;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;

/**
 * Informacje o połączeniu między dwoma miejscami, lotach, które te połączenie obsługują, lotnisku, z którego
 * te loty odlatują oraz bazowej cenie połączenia
 */

@Entity
public class Connection implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false)
    private Long id;

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "destination_id", nullable = false)
    private Airport destination;

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "source_id", nullable = false)
    private Airport source;

    @Digits(integer = 7, fraction = 2)
    @NotNull
    @Column(precision = 7, scale = 2, nullable = false)
    private BigDecimal basePrice;

    @Version
    private Long version;

    public Connection() {
    }

    public Connection(Airport destination, Airport source, @Digits(integer = 7, fraction = 2) @NotNull BigDecimal basePrice) {
        this.destination = destination;
        this.source = source;
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

    public Long getId() {
        return id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Connection)) return false;
        Connection that = (Connection) o;
        return Objects.equals(destination, that.destination) &&
                Objects.equals(source, that.source) &&
                Objects.equals(basePrice, that.basePrice) &&
                Objects.equals(version, that.version);
    }

    @Override
    public int hashCode() {
        return Objects.hash(destination, source, basePrice, version);
    }
}

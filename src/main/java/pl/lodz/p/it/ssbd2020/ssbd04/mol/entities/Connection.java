package pl.lodz.p.it.ssbd2020.ssbd04.mol.entities;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.List;
import java.util.Objects;

/**
 * Informacje o połączeniu między dwoma miejscami, lotach, które te połączenie obsługują, lotnisku, z którego
 * te loty odlatują oraz bazowej cenie połączenia
 */

@Entity
@Table(name = "Connection")
public class Connection implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false, nullable = false)
    private long id;

    @ManyToOne
    private Airport destination;

    @ManyToOne
    private Airport source;

    @NotNull
    private Float basePrice;

    @Version
    private Long version;

    public Connection() {}

    public Connection(Airport source, Airport destination, Float basePrice, Long version) {
        this.source = source;
        this.destination = destination;
        this.basePrice = basePrice;
        this.version = version;
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

    public void setBasePrice(Float basePrice) {
        this.basePrice = basePrice;
    }

    public Float getBasePrice() {
        return basePrice;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Long getVersion() {
        return version;
    }

    public void setVersion(Long version) {
        this.version = version;
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

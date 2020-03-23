package pl.lodz.p.it.ssbd2020.ssbd04.mol.entities;

import javax.persistence.*;
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
    private String destination;
    private String source;

    private Float basePrice;

    @Version
    private Long version;

    public Connection() {}

    public Connection(String destination, String source, Float basePrice, Long version) {
        this.destination = destination;
        this.source = source;
        this.basePrice = basePrice;
        this.version = version;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public float getBasePrice() {
        return basePrice;
    }

    public void setBasePrice(float basePrice) {
        this.basePrice = basePrice;
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

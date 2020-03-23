package pl.lodz.p.it.ssbd2020.ssbd04.mol.entities;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * Opis struktury samolotu (liczba kolumn i rzędów)
 */

@Entity
@Table(name = "AirplaneSchema")
public class AirplaneSchema implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false, nullable = false)
    private Long id;

    private Integer rows;
    private Integer cols;

    @Version
    private Long version;

    public AirplaneSchema() {}

    public AirplaneSchema(Integer rows, Integer cols, Long version) {
        this.rows = rows;
        this.cols = cols;
        this.version = version;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getRows() {
        return rows;
    }

    public void setRows(Integer rows) {
        this.rows = rows;
    }

    public Integer getCols() {
        return cols;
    }

    public void setCols(Integer cols) {
        this.cols = cols;
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
        if (!(o instanceof AirplaneSchema)) return false;
        AirplaneSchema that = (AirplaneSchema) o;
        return Objects.equals(rows, that.rows) &&
                Objects.equals(cols, that.cols) &&
                Objects.equals(version, that.version);
    }

    @Override
    public int hashCode() {
        return Objects.hash(rows, cols, version);
    }
}

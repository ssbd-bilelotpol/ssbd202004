package pl.lodz.p.it.ssbd2020.ssbd04.mol.entities;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigInteger;

/**
 * Opis struktury samolotu (liczba kolumn i rzędów)
 */

@Entity
@Table(name = "AirplaneSchema")
public class AirplaneSchema implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id", updatable = false, nullable = false)
    private Long id;

    private Integer rows;
    private Integer cols;

    private BigInteger version;

    public AirplaneSchema() {}

    public AirplaneSchema(Integer rows, Integer cols, BigInteger version) {
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

    public BigInteger getVersion() {
        return version;
    }

    public void setVersion(BigInteger version) {
        this.version = version;
    }
}

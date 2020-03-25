package pl.lodz.p.it.ssbd2020.ssbd04.mol.entities;

import pl.lodz.p.it.ssbd2020.ssbd04.mob.entities.Seat;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.List;
import java.util.Objects;
import java.util.Set;

/**
 * Opis struktury samolotu (liczba kolumn i rzędów)
 */

@Entity
@Table(name = "airplane_schema")
public class AirplaneSchema implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false)
    private Long id;

    @Column(nullable = false)
    @Min(value = 1L)
    private Integer rows;

    @Column(nullable = false)
    @Min(value = 1L)
    private Integer cols;

    @Column(nullable = false)
    @OneToMany
    @JoinColumn(name = "schema_id")
    private Set<Seat> seatList;

    @Version
    private Long version;

    public AirplaneSchema() {}

    public AirplaneSchema(@NotNull Integer rows, @NotNull Integer cols, Set<Seat> seatList, Long version) {
        this.rows = rows;
        this.cols = cols;
        this.seatList = seatList;
        this.version = version;
    }

    public Long getId() {
        return id;
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

    public Set<Seat> getSeatList() {
        return seatList;
    }

    public void setSeatList(Set<Seat> seatList) {
        this.seatList = seatList;
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
                Objects.equals(seatList, that.seatList) &&
                Objects.equals(version, that.version);
    }

    @Override
    public int hashCode() {
        return Objects.hash(rows, cols, seatList, version);
    }
}

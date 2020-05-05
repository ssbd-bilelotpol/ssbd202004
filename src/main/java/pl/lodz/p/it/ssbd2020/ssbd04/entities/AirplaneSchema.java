package pl.lodz.p.it.ssbd2020.ssbd04.entities;

import pl.lodz.p.it.ssbd2020.ssbd04.common.AbstractEntity;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * Opis struktury samolotu (liczba kolumn i rzędów)
 */

@Entity
@Table(name = "airplane_schema")
public class AirplaneSchema extends AbstractEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false)
    private Long id;

    @Column(nullable = false)
    @Min(value = 1)
    private Integer rows;

    @Column(nullable = false)
    @Min(value = 1)
    private Integer cols;

    @NotNull
    @Column(nullable = false)
    @OneToMany(cascade = {CascadeType.REMOVE, CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH},
            orphanRemoval = true, mappedBy = "airplaneSchema")
    private Set<Seat> seatList = new HashSet<>();

    public AirplaneSchema() {
    }

    public AirplaneSchema(@Min(value = 1L) Integer rows, @Min(value = 1L) Integer cols, Set<Seat> seatList) {
        this.rows = rows;
        this.cols = cols;
        this.seatList = seatList;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AirplaneSchema that = (AirplaneSchema) o;
        return rows.equals(that.rows) &&
                cols.equals(that.cols) &&
                Objects.equals(seatList, that.seatList);
    }

    @Override
    public int hashCode() {
        return Objects.hash(rows, cols, seatList);
    }

    //TODO: Generate toString() once implemented
}

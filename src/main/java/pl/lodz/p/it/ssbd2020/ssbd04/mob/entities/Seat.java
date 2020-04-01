package pl.lodz.p.it.ssbd2020.ssbd04.mob.entities;

import pl.lodz.p.it.ssbd2020.ssbd04.mol.entities.AirplaneSchema;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Objects;

/**
 * Informacje o numerze siedzenia i jego umiejscowieniu na planie samolotu
 */
@Entity
public class Seat implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(updatable = false)
    private Long id;

    @NotNull
    @Column(nullable = false)
    private Integer col;

    @NotNull
    @Column(nullable = false)
    private Integer row;

    @ManyToOne(cascade = {CascadeType.REFRESH})
    @JoinColumn(name = "seat_class_id", nullable = false)
    private SeatClass seatClass;

    @ManyToOne(cascade = {CascadeType.REFRESH})
    @JoinColumn(name = "airplane_schema_id", nullable = false)
    private AirplaneSchema airplaneSchema;

    @Version
    private Long version;

    public Seat() {
    }

    public Seat(@NotNull Integer col, @NotNull Integer row, SeatClass seatClass, AirplaneSchema airplaneSchema) {
        this.col = col;
        this.row = row;
        this.seatClass = seatClass;
        this.airplaneSchema = airplaneSchema;
    }

    public Long getId() {
        return id;
    }

    public Integer getCol() {
        return col;
    }

    public void setCol(Integer col) {
        this.col = col;
    }

    public Integer getRow() {
        return row;
    }

    public void setRow(Integer row) {
        this.row = row;
    }

    public SeatClass getSeatClass() {
        return seatClass;
    }

    public void setSeatClass(SeatClass seatClass) {
        this.seatClass = seatClass;
    }

    public AirplaneSchema getAirplaneSchema() {
        return airplaneSchema;
    }

    public void setAirplaneSchema(AirplaneSchema airplaneSchema) {
        this.airplaneSchema = airplaneSchema;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Seat)) return false;

        Seat seat = (Seat) o;

        if (!Objects.equals(col, seat.col)) return false;
        return Objects.equals(row, seat.row);
    }

    @Override
    public int hashCode() {
        int result = col != null ? col.hashCode() : 0;
        result = 31 * result + (row != null ? row.hashCode() : 0);
        return result;
    }
}

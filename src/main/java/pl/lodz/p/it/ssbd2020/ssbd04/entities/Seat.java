package pl.lodz.p.it.ssbd2020.ssbd04.entities;

import pl.lodz.p.it.ssbd2020.ssbd04.common.AbstractEntity;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Objects;

/**
 * Informacje o numerze siedzenia i jego umiejscowieniu na planie samolotu
 */
@Entity
@Table(
        indexes = {
                @Index(name = "seat_seat_class_fk", columnList = "seat_class_id"),
                @Index(name = "seat_airplane_schema_fk", columnList = "airplane_schema_id")
        }
)
public class Seat extends AbstractEntity implements Serializable {

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

    @NotNull
    @ManyToOne(cascade = {CascadeType.REFRESH})
    @JoinColumn(name = "seat_class_id", nullable = false, foreignKey = @ForeignKey(name = "seat_seat_class_fk"))
    private SeatClass seatClass;

    @NotNull
    @ManyToOne(cascade = {CascadeType.REFRESH})
    @JoinColumn(name = "airplane_schema_id", nullable = false, updatable = false, foreignKey = @ForeignKey(name = "seat_airplane_schema_fk"))
    private AirplaneSchema airplaneSchema;

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
        return col.equals(seat.col) &&
                row.equals(seat.row) &&
                seatClass.equals(seat.seatClass) &&
                airplaneSchema.equals(seat.airplaneSchema);
    }

    @Override
    public int hashCode() {
        return Objects.hash(col, row, seatClass, airplaneSchema);
    }

    @Override
    public String toString() {
        return "Seat{" +
                "id=" + id +
                ", col=" + col +
                ", row=" + row +
                ", seatClass=" + seatClass.getName() +
                "}";
    }
}

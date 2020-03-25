package pl.lodz.p.it.ssbd2020.ssbd04.mol.entities;

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
    @Column(name = "id", updatable = false, nullable = false)
    private Long id;

    @NotNull
    private Integer col;

    @NotNull
    private Integer row;

    @NotNull
    private Integer number;

    @OneToOne(targetEntity = SeatClass.class)
    @NotNull
    private SeatClass seatClass;

    @Version
    private Long version;


    public Seat() {
    }

    public Seat(Integer col, Integer row, Integer number, SeatClass seatClass, Long version){
        this.col = col;
        this.row = row;
        this.number = number;
        this.seatClass = seatClass;
        this.version = version;
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

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    public SeatClass getSeatClass() {
        return seatClass;
    }

    public void setSeatClass(SeatClass _class) {
        this.seatClass = _class;
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
        if (!(o instanceof Seat)) return false;
        Seat seat = (Seat) o;
        return Objects.equals(col, seat.col) &&
                Objects.equals(row, seat.row) &&
                Objects.equals(number, seat.number) &&
                Objects.equals(seatClass, seat.seatClass) &&
                Objects.equals(version, seat.version);
    }

    @Override
    public int hashCode() {
        return Objects.hash(col, row, number, seatClass, version);
    }
}

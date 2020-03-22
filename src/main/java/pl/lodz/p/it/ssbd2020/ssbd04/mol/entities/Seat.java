package pl.lodz.p.it.ssbd2020.ssbd04.mol.entities;

import javax.persistence.*;
import java.math.BigInteger;

/**
 * Informacje o numerze siedzenia i jego umiejscowieniu na planie samolotu
 */

@Entity
@Table(name = "Seat")
public class Seat {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    private Integer col;
    private Integer row;
    private Integer number;

    @OneToOne(targetEntity = SeatClass.class)
    private SeatClass _class;

    private BigInteger version;


    public Seat() {
    }

    public Seat(Integer col, Integer row, Integer number, SeatClass _class, BigInteger version){
        this.col = col;
        this.row = row;
        this.number = number;
        this._class = _class;
        this.version = version;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public SeatClass get_class() {
        return _class;
    }

    public void set_class(SeatClass _class) {
        this._class = _class;
    }

    public BigInteger getVersion() {
        return version;
    }

    public void setVersion(BigInteger version) {
        this.version = version;
    }
}

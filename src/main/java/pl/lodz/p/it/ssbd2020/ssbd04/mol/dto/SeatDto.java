package pl.lodz.p.it.ssbd2020.ssbd04.mol.dto;

import pl.lodz.p.it.ssbd2020.ssbd04.validation.SeatClassName;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * Reprezentuje siedzenie w warstwie prezentacji
 */
public class SeatDto {
    private Long id;

    @NotNull
    @Size(min = 2, max = 30)
    @SeatClassName
    private String seatClass;

    @NotNull
    @Min(1)
    @Max(8)
    private Integer col;

    @NotNull
    @Min(1)
    @Max(40)
    private Integer row;

    public SeatDto() {
    }

    public SeatDto(Long id, String seatClass, Integer col, Integer row) {
        this.id = id;
        this.seatClass = seatClass;
        this.col = col;
        this.row = row;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSeatClass() {
        return seatClass;
    }

    public void setSeatClass(String seatClass) {
        this.seatClass = seatClass;
    }

    public Integer getRow() {
        return row;
    }

    public void setRow(Integer row) {
        this.row = row;
    }

    public Integer getCol() {
        return col;
    }

    public void setCol(Integer col) {
        this.col = col;
    }
}

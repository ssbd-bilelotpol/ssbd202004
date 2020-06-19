package pl.lodz.p.it.ssbd2020.ssbd04.mob.dto;

import pl.lodz.p.it.ssbd2020.ssbd04.entities.Seat;

/**
 * Reprezentuje siedzenie w warstwie prezentacji
 */
public class SeatDto {

    private Long id;
    private SeatClassDto seatClass;
    private Integer col;
    private Integer row;

    public SeatDto() {
    }

    public SeatDto(Seat seat) {
        this.id = seat.getId();
        this.seatClass = new SeatClassDto(seat.getSeatClass());
        this.col = seat.getCol();
        this.row = seat.getRow();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public SeatClassDto getSeatClass() {
        return seatClass;
    }

    public void setSeatClass(SeatClassDto seatClass) {
        this.seatClass = seatClass;
    }

}

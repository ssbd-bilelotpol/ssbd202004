package pl.lodz.p.it.ssbd2020.ssbd04.mol.entities;

public class AirplaneSchema {
    private long id;
    private int rows;
    private int cols;

    public AirplaneSchema(long id, int rows, int cols) {
        this.id = id;
        this.rows = rows;
        this.cols = cols;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getRows() {
        return rows;
    }

    public void setRows(int rows) {
        this.rows = rows;
    }

    public int getCols() {
        return cols;
    }

    public void setCols(int cols) {
        this.cols = cols;
    }
}

package pl.lodz.p.it.ssbd2020.ssbd04.mol.dto;

import pl.lodz.p.it.ssbd2020.ssbd04.entities.AirplaneSchema;

/**
 * Przenosi dane schematu samolotu do warstwy prezentacji.
 */
public class AirplaneSchemaDto {
    private String name;
    private Long id;
    private Integer cols;
    private Integer rows;

    public AirplaneSchemaDto() {
    }

    public AirplaneSchemaDto(AirplaneSchema airplaneSchema) {
        //TODO: remove this
        this.name = "Airbus 3000";
        this.id = airplaneSchema.getId();
        this.cols = airplaneSchema.getCols();
        this.rows = airplaneSchema.getRows();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getCols() {
        return cols;
    }

    public void setCols(Integer cols) {
        this.cols = cols;
    }

    public Integer getRows() {
        return rows;
    }

    public void setRows(Integer rows) {
        this.rows = rows;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}

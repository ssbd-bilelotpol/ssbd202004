package pl.lodz.p.it.ssbd2020.ssbd04.mol.dto;

import pl.lodz.p.it.ssbd2020.ssbd04.entities.AirplaneSchema;

import javax.json.bind.annotation.JsonbTransient;

/**
 * Przenosi część danych schematu samolotu do warstwy prezentacji
 */
public class AirplaneSchemaListDto {
    private Long id;
    private String name;
    private int rows;
    private int columns;
    @JsonbTransient
    private Long version;

    public AirplaneSchemaListDto() {

    }

    public AirplaneSchemaListDto(AirplaneSchema airplaneSchema) {
        this.id = airplaneSchema.getId();
        this.name = airplaneSchema.getName();
        this.rows = airplaneSchema.getRows();
        this.columns = airplaneSchema.getCols();
        this.version = airplaneSchema.getVersion();
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getRows() {
        return rows;
    }

    public int getColumns() {
        return columns;
    }

    public Long getVersion() {
        return version;
    }
}

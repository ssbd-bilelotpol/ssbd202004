package pl.lodz.p.it.ssbd2020.ssbd04.mol.dto;

import pl.lodz.p.it.ssbd2020.ssbd04.entities.AirplaneSchema;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Przenosi dane schematu samolotu do warstwy prezentacji.
 */
public class AirplaneSchemaListDto {
    private Long id;
    private String name;
    private int rows;
    private int columns;

    public AirplaneSchemaListDto() {

    }

    public AirplaneSchemaListDto(AirplaneSchema airplaneSchema) {
        this.id = airplaneSchema.getId();
        this.name = airplaneSchema.getName();
        this.rows = airplaneSchema.getRows();
        this.columns = airplaneSchema.getCols();
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
}

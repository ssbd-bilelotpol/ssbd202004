package pl.lodz.p.it.ssbd2020.ssbd04.mol.dto;

import pl.lodz.p.it.ssbd2020.ssbd04.entities.AirplaneSchema;
import pl.lodz.p.it.ssbd2020.ssbd04.security.Signable;
import pl.lodz.p.it.ssbd2020.ssbd04.validation.AirplaneSchemaName;

import javax.json.bind.annotation.JsonbTransient;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static pl.lodz.p.it.ssbd2020.ssbd04.common.Utils.isNullOrEmpty;

/**
 * Przenosi dane schematu samolotu do warstwy prezentacji.
 */
public class AirplaneSchemaDto implements Signable {
    private Long id;

    @AirplaneSchemaName
    @NotNull
    private String name;

    @Min(3)
    @Max(40)
    @NotNull
    private Integer rows;

    @Min(2)
    @Max(9)
    @NotNull
    private Integer columns;

    private Set<Integer> emptyRows;

    private Set<Integer> emptyColumns;

    @NotNull
    private List<SeatDto> seats;

    @JsonbTransient
    private Long version;

    public AirplaneSchemaDto() {

    }

    private Set<Integer> explode(String nums) {
        if (isNullOrEmpty(nums)) {
            return new HashSet<>();
        }
        return Arrays.stream(nums.split(","))
                .map(Integer::valueOf)
                .collect(Collectors.toSet());
    }

    public AirplaneSchemaDto(AirplaneSchema airplaneSchema) {
        this.id = airplaneSchema.getId();
        this.name = airplaneSchema.getName();
        this.rows = airplaneSchema.getRows();
        this.columns = airplaneSchema.getCols();
        this.emptyRows = explode(airplaneSchema.getEmptyRows());
        this.emptyColumns = explode(airplaneSchema.getEmptyColumns());
        this.seats = airplaneSchema.getSeatList()
                .stream()
                .map(s -> new SeatDto(s.getId(), s.getSeatClass().getName(), s.getCol(), s.getRow())).collect(Collectors.toList());
        this.version = airplaneSchema.getVersion();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getRows() {
        return rows;
    }

    public void setRows(Integer rows) {
        this.rows = rows;
    }

    public Integer getColumns() {
        return columns;
    }

    public void setColumns(Integer columns) {
        this.columns = columns;
    }

    public Set<Integer> getEmptyRows() {
        return emptyRows;
    }

    public void setEmptyRows(Set<Integer> emptyRows) {
        this.emptyRows = emptyRows;
    }

    public Set<Integer> getEmptyColumns() {
        return emptyColumns;
    }

    public void setEmptyColumns(Set<Integer> emptyColumns) {
        this.emptyColumns = emptyColumns;
    }

    public List<SeatDto> getSeats() {
        return seats;
    }

    public void setSeats(List<SeatDto> seats) {
        this.seats = seats;
    }

    public Long getVersion() {
        return version;
    }

    @Override
    public String createMessage() {
        return String.format("%d.%s.%s", this.version, this.id, this.name);
    }
}

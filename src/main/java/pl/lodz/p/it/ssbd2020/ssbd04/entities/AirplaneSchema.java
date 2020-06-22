package pl.lodz.p.it.ssbd2020.ssbd04.entities;

import pl.lodz.p.it.ssbd2020.ssbd04.common.AbstractEntity;
import pl.lodz.p.it.ssbd2020.ssbd04.validation.AirplaneSchemaName;
import pl.lodz.p.it.ssbd2020.ssbd04.validation.AirplaneSchemaRowCol;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * Opis struktury samolotu (liczba kolumn i rzędów)
 */

@NamedQueries({
        @NamedQuery(name = "AirplaneSchema.findByName",
                query = "SELECT airplaneSchema from AirplaneSchema airplaneSchema WHERE LOWER(airplaneSchema.name) LIKE LOWER(CONCAT('%', :name, '%')) "
        )
})
@Entity
@Table(
        name = "airplane_schema",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = "name", name = AirplaneSchema.CONSTRAINT_SCHEMA_NAME),
        }
)
public class AirplaneSchema extends AbstractEntity implements Serializable {
    public static final String CONSTRAINT_IN_USE = "flight_airplane_schema_fk";
    public static final String CONSTRAINT_SCHEMA_NAME = "airplane_schema_name_unique";
    public static final String CONSTRAINT_SEAT_USE = "passenger_seat_fk";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false)
    private Long id;

    @AirplaneSchemaName
    @Column(nullable = false)
    private String name;

    @Column(name = "empty_rows")
    @AirplaneSchemaRowCol
    private String emptyRows;

    @Column(name = "empty_columns")
    @AirplaneSchemaRowCol
    private String emptyColumns;

    @Column(nullable = false)
    @Min(value = 1)
    @Max(40)
    private Integer rows;

    @Column(nullable = false)
    @Min(value = 1)
    @Max(8)
    private Integer cols;

    @NotNull
    @Column(nullable = false)
    @OneToMany(cascade = {CascadeType.REMOVE, CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH},
            orphanRemoval = true, mappedBy = "airplaneSchema")
    private Set<Seat> seatList = new HashSet<>();

    public AirplaneSchema() {
    }

    public AirplaneSchema(@Min(value = 1L) Integer rows, @Min(value = 1L) Integer cols, Set<Seat> seatList) {
        this.rows = rows;
        this.cols = cols;
        this.seatList = seatList;
    }

    public Long getId() {
        return id;
    }

    public Integer getRows() {
        return rows;
    }

    public void setRows(Integer rows) {
        this.rows = rows;
    }

    public Integer getCols() {
        return cols;
    }

    public void setCols(Integer cols) {
        this.cols = cols;
    }

    public Set<Seat> getSeatList() {
        return seatList;
    }

    public void setSeatList(Set<Seat> seatList) {
        this.seatList = seatList;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AirplaneSchema that = (AirplaneSchema) o;
        return rows.equals(that.rows) &&
                cols.equals(that.cols) &&
                Objects.equals(seatList, that.seatList);
    }

    public String getEmptyRows() {
        return emptyRows;
    }

    public void setEmptyRows(String emptyRows) {
        this.emptyRows = emptyRows;
    }

    public String getEmptyColumns() {
        return emptyColumns;
    }

    public void setEmptyColumns(String emptyColumns) {
        this.emptyColumns = emptyColumns;
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, emptyColumns, emptyRows, rows, cols);
    }

    @Override
    public String toString() {
        return "AirplaneSchema{" +
                "id=" + id +
                ", version=" + getVersion() +
                '}';
    }
}

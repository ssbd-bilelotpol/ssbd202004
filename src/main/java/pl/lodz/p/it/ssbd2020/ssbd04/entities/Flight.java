package pl.lodz.p.it.ssbd2020.ssbd04.entities;

import pl.lodz.p.it.ssbd2020.ssbd04.common.AbstractEntity;

import javax.persistence.*;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Objects;

/**
 * Informacje o konkretnym locie, połączeniu, którego jest częścią, kodzie lotu, samolocie oraz dacie początku i końca
 * przelotu
 */
@NamedQueries({
        @NamedQuery(name = "Flight.findByCode",
                query = "SELECT flight from Flight flight WHERE flight.flightCode = :code"),
        @NamedQuery(name = "Flight.getDates",
                query = "SELECT distinct cast(startDateTime as date) from Flight flight where startDateTime > :from"),
        @NamedQuery(name = "Flight.findByAirplaneSchema",
                query = "SELECT flight from Flight flight WHERE flight.airplaneSchema.id = :schemaId"),
        @NamedQuery(name = "Flight.findAccountsByTicketsForFlight",
                query = "SELECT DISTINCT ticket.account FROM Ticket ticket JOIN ticket.flight flight WHERE flight.flightCode = :code")
})
@Entity
@Table(
        name = "flight",
        indexes = {
                @Index(name = "flight_connection_fk", columnList = "connection_id"),
                @Index(name = "flight_airplane_schema_fk", columnList = "airplane_schema_id")
        },
        uniqueConstraints = {
                @UniqueConstraint(name = Flight.CONSTRAINT_FLIGHT_CODE, columnNames = "flight_code")
        }
)
public class Flight extends AbstractEntity implements Serializable {
    public static final String CONSTRAINT_FLIGHT_CODE = "flight_flight_code_unique";

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "flight_generator")
    @SequenceGenerator(name = "flight_generator", sequenceName = "flight_seq", allocationSize = 10)
    @Column(name = "id", updatable = false)
    private Long id;

    @NotNull
    @Column(nullable = false, length = 30, updatable = false, name = "flight_code")
    @Size(min = 5, max = 30)
    private String flightCode;

    @Digits(integer = 7, fraction = 2)
    @NotNull
    @Column(precision = 7, scale = 2, nullable = false)
    private BigDecimal price;

    @NotNull
    @ManyToOne(cascade = {CascadeType.REFRESH})
    @JoinColumn(name = "connection_id", nullable = false, updatable = false, foreignKey = @ForeignKey(name = "flight_connection_fk"))
    private Connection connection;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, cascade = {CascadeType.REFRESH})
    @JoinColumn(name = "airplane_schema_id", nullable = false, updatable = false, foreignKey = @ForeignKey(name = "flight_airplane_schema_fk"))
    private AirplaneSchema airplaneSchema;

    @Column(nullable = false, name = "start_date_time")
    private LocalDateTime startDateTime;

    @Column(nullable = false, name = "end_date_time")
    private LocalDateTime endDateTime;

    @Column(nullable = false, length = 64)
    @Enumerated(EnumType.STRING)
    private FlightStatus status;

    public Flight() {
    }

    public Flight(@NotNull @Size(min = 5, max = 30) String flightCode,
                  @Digits(integer = 7, fraction = 2) @NotNull BigDecimal price, Connection connection,
                  AirplaneSchema airplaneSchema, LocalDateTime startDateTime, LocalDateTime endDateTime,
                  FlightStatus status) {
        this.flightCode = flightCode;
        this.price = price;
        this.connection = connection;
        this.airplaneSchema = airplaneSchema;
        this.startDateTime = startDateTime;
        this.endDateTime = endDateTime;
        this.status = status;
    }

    public Long getId() {
        return id;
    }

    public String getFlightCode() {
        return flightCode;
    }

    public void setFlightCode(String flightCode) {
        this.flightCode = flightCode;
    }

    public Connection getConnection() {
        return connection;
    }

    public void setConnection(Connection connection) {
        this.connection = connection;
    }

    public AirplaneSchema getAirplaneSchema() {
        return airplaneSchema;
    }

    public void setAirplaneSchema(AirplaneSchema airplaneSchema) {
        this.airplaneSchema = airplaneSchema;
    }

    public LocalDateTime getStartDateTime() {
        return startDateTime;
    }

    public void setStartDateTime(LocalDateTime startDatetime) {
        this.startDateTime = startDatetime;
    }

    public LocalDateTime getEndDateTime() {
        return endDateTime;
    }

    public void setEndDateTime(LocalDateTime endDatetime) {
        this.endDateTime = endDatetime;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public FlightStatus getStatus() {
        return status;
    }

    public void setStatus(FlightStatus status) {
        this.status = status;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Flight flight = (Flight) o;
        return flightCode.equals(flight.flightCode);
    }

    @Override
    public int hashCode() {
        return Objects.hash(flightCode);
    }

    @Override
    public String toString() {
        return "Flight{" +
                "id=" + id +
                ", version=" + getVersion() +
                "}";
    }
}

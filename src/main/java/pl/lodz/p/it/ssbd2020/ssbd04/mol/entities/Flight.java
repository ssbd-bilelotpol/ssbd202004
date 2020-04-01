package pl.lodz.p.it.ssbd2020.ssbd04.mol.entities;

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

@Entity
public class Flight implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "flight_generator")
    @SequenceGenerator(name = "flight_generator", sequenceName = "flight_seq", allocationSize = 10)
    @Column(name = "id", updatable = false)
    private Long id;

    @NotNull
    @Column(nullable = false, unique = true, length = 30, updatable = false, name = "flight_code")
    @Size(min = 5, max = 30)
    private String flightCode;

    @Digits(integer = 7, fraction = 2)
    @NotNull
    @Column(precision = 7, scale = 2, nullable = false)
    private BigDecimal price;

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.REFRESH})
    @JoinColumn(name = "connection_id", nullable = false)
    private Connection connection;

    @ManyToOne(fetch = FetchType.LAZY, cascade = {CascadeType.REFRESH})
    @JoinColumn(name = "airplane_schema_id", nullable = false)
    private AirplaneSchema airplaneSchema;

    @Column(nullable = false, name = "start_date_time")
    private LocalDateTime startDateTime;

    @Column(nullable = false, name = "end_date_time")
    private LocalDateTime endDateTime;

    @Column(nullable = false, length = 64)
    @Enumerated(EnumType.STRING)
    private FlightStatus status;

    @Version
    private Long version;

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
        if (!(o instanceof Flight)) return false;
        Flight flight = (Flight) o;
        return Objects.equals(flightCode, flight.flightCode) &&
                Objects.equals(connection, flight.connection) &&
                Objects.equals(airplaneSchema, flight.airplaneSchema) &&
                Objects.equals(startDateTime, flight.startDateTime) &&
                Objects.equals(endDateTime, flight.endDateTime) &&
                Objects.equals(version, flight.version);
    }

    @Override
    public int hashCode() {
        return Objects.hash(flightCode, connection, airplaneSchema, startDateTime, endDateTime, version);
    }
}

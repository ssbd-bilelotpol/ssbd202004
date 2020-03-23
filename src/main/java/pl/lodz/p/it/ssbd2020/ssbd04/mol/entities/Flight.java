package pl.lodz.p.it.ssbd2020.ssbd04.mol.entities;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Objects;

/**
 * Informacje o konkretnym locie, połączeniu, którego jest częścią, kodzie lotu, samolocie oraz dacie początku i końca
 * przelotu
 */

@Entity
@Table(name = "Flight")
public class Flight implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false, nullable = false)
    private long id;
    private String flightCode;

    @ManyToOne(targetEntity = Connection.class)
    @NotNull
    private Connection connection;

    @ManyToOne(targetEntity = AirplaneSchema.class)
    @NotNull
    private AirplaneSchema airplaneSchema;

    @NotNull
    private LocalDateTime startDatetime;

    @NotNull
    private LocalDateTime endDatetime;

    @Version
    private Long version;

    public Flight() {}

    public Flight(String flightCode, Connection connection, AirplaneSchema airplaneSchema, LocalDateTime startDatetime, LocalDateTime endDatetime, Long version) {
        this.flightCode = flightCode;
        this.connection = connection;
        this.airplaneSchema = airplaneSchema;
        this.startDatetime = startDatetime;
        this.endDatetime = endDatetime;
        this.version = version;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
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

    public LocalDateTime getStartDatetime() {
        return startDatetime;
    }

    public void setStartDatetime(LocalDateTime startDatetime) {
        this.startDatetime = startDatetime;
    }

    public LocalDateTime getEndDatetime() {
        return endDatetime;
    }

    public void setEndDatetime(LocalDateTime endDatetime) {
        this.endDatetime = endDatetime;
    }

    public Long getVersion() {
        return version;
    }

    public void setVersion(Long version) {
        this.version = version;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Flight)) return false;
        Flight flight = (Flight) o;
        return Objects.equals(flightCode, flight.flightCode) &&
                Objects.equals(connection, flight.connection) &&
                Objects.equals(airplaneSchema, flight.airplaneSchema) &&
                Objects.equals(startDatetime, flight.startDatetime) &&
                Objects.equals(endDatetime, flight.endDatetime) &&
                Objects.equals(version, flight.version);
    }

    @Override
    public int hashCode() {
        return Objects.hash(flightCode, connection, airplaneSchema, startDatetime, endDatetime, version);
    }
}

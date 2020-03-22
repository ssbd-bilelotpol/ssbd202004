package pl.lodz.p.it.ssbd2020.ssbd04.mol.entities;

import javax.persistence.*;
import java.math.BigInteger;
import java.time.LocalDateTime;

/**
 * Informacje o konkretnym locie, połączeniu, którego jest częścią, kodzie lotu, samolocie oraz dacie początku i końca
 * przelotu
 */

@Entity
@Table(name = "Flight")
public class Flight {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id", updatable = false, nullable = false)
    private long id;
    private String flightCode;

    @ManyToOne(targetEntity = Connection.class)
    private Connection connection;

    @ManyToOne(targetEntity = AirplaneSchema.class)
    private AirplaneSchema airplaneSchema;
    private LocalDateTime startDatetime;
    private LocalDateTime endDatetime;

    private BigInteger version;

    public Flight() {}

    public Flight(String flightCode, Connection connection, AirplaneSchema airplaneSchema, LocalDateTime startDatetime, LocalDateTime endDatetime, BigInteger version) {
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

    public BigInteger getVersion() {
        return version;
    }

    public void setVersion(BigInteger version) {
        this.version = version;
    }
}

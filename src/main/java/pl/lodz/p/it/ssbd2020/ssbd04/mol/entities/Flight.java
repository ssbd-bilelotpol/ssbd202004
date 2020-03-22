package pl.lodz.p.it.ssbd2020.ssbd04.mol.entities;

import java.time.LocalDateTime;

public class Flight {
    private long id;
    private String flightCode;
    private Connection connection;
    private AirplaneSchema airplaneSchema;
    private LocalDateTime startDatetime;
    private LocalDateTime endDatetime;

    public Flight(long id, String flightCode, Connection connection, AirplaneSchema airplaneSchema, LocalDateTime startDatetime, LocalDateTime endDatetime) {
        this.id = id;
        this.flightCode = flightCode;
        this.connection = connection;
        this.airplaneSchema = airplaneSchema;
        this.startDatetime = startDatetime;
        this.endDatetime = endDatetime;
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
}

package pl.lodz.p.it.ssbd2020.ssbd04.mob.dto;

import pl.lodz.p.it.ssbd2020.ssbd04.entities.Flight;
import pl.lodz.p.it.ssbd2020.ssbd04.entities.FlightStatus;

import javax.json.bind.annotation.JsonbDateFormat;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * Reprezentuje lot
 */
public class FlightDto {

    private String code;

    private BigDecimal price;

    private ConnectionDto connection;

    @JsonbDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
    private LocalDateTime startDateTime;

    @JsonbDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
    private LocalDateTime endDateTime;

    private FlightStatus status;

    public FlightDto() {
    }

    public FlightDto(Flight flight) {
        this.code = flight.getFlightCode();
        this.price = flight.getPrice();
        this.connection = new ConnectionDto(flight.getConnection());
        this.startDateTime = flight.getStartDateTime();
        this.endDateTime = flight.getEndDateTime();
        this.status = flight.getStatus();
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public ConnectionDto getConnection() {
        return connection;
    }

    public void setConnection(ConnectionDto connection) {
        this.connection = connection;
    }

    public LocalDateTime getStartDateTime() {
        return startDateTime;
    }

    public void setStartDateTime(LocalDateTime startDateTime) {
        this.startDateTime = startDateTime;
    }

    public LocalDateTime getEndDateTime() {
        return endDateTime;
    }

    public void setEndDateTime(LocalDateTime endDateTime) {
        this.endDateTime = endDateTime;
    }

    public FlightStatus getStatus() {
        return status;
    }

    public void setStatus(FlightStatus status) {
        this.status = status;
    }
    
}

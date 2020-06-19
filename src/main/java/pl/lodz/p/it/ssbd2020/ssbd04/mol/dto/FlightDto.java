package pl.lodz.p.it.ssbd2020.ssbd04.mol.dto;

import pl.lodz.p.it.ssbd2020.ssbd04.entities.Flight;
import pl.lodz.p.it.ssbd2020.ssbd04.entities.FlightStatus;
import pl.lodz.p.it.ssbd2020.ssbd04.security.Signable;

import javax.json.bind.annotation.JsonbDateFormat;
import javax.json.bind.annotation.JsonbTransient;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * Reprezentuje lot
 */
public class FlightDto implements Signable {
    private String code;
    private BigDecimal price;
    private ConnectionDto connection;
    private AirplaneSchemaListDto airplaneSchema;
    @JsonbDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
    private LocalDateTime startDateTime;
    @JsonbDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
    private LocalDateTime endDateTime;
    private FlightStatus status;
    @JsonbTransient
    private Long version;

    public FlightDto() {
    }

    public FlightDto(Flight flight) {
        this.code = flight.getFlightCode();
        this.price = flight.getPrice();
        this.connection = new ConnectionDto(flight.getConnection());
        this.airplaneSchema = new AirplaneSchemaListDto(flight.getAirplaneSchema());
        this.startDateTime = flight.getStartDateTime();
        this.endDateTime = flight.getEndDateTime();
        this.status = flight.getStatus();
        this.version = flight.getVersion();
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

    public AirplaneSchemaListDto getAirplaneSchema() {
        return airplaneSchema;
    }

    public void setAirplaneSchema(AirplaneSchemaListDto airplaneSchema) {
        this.airplaneSchema = airplaneSchema;
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

    public Long getVersion() {
        return version;
    }

    @Override
    public String createMessage() {
        return String.format("%d.%s", this.version, this.code);
    }
}

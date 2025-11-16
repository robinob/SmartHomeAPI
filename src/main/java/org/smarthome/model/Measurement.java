package org.smarthome.model;

import jakarta.persistence.*;
import lombok.Data; // Provides Getters, Setters, etc. automatically
import java.time.LocalDateTime;

@Entity // Marks this class as a JPA entity (maps to a DB table)
@Table(name = "measurements")
@Data
public class Measurement {
    @Id // Specifies the primary key
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Uses the DB's auto-increment
    private Long id;

    // Fields for environmental data
    private Double temperature;
    private Double humidity;
    private Double co2Level;

    // Timestamp for when the data was recorded
    private LocalDateTime timestamp;

    // Default constructor required by JPA
    protected Measurement() {
    }

    // Convenient constructor for creating new data records
    public Measurement(Double temperature, Double humidity, Double co2Level) {
        this.temperature = temperature;
        this.humidity = humidity;
        this.co2Level = co2Level;
        this.timestamp = LocalDateTime.now();
    }
}
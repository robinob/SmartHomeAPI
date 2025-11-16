package org.smarthome.service;

import org.smarthome.model.Measurement;
import org.smarthome.repository.MeasurementRepository;
import org.springframework.stereotype.Service;
import java.util.Optional;

@Service // Marks this class as a Spring Service component
public class MeasurementService {

    private final MeasurementRepository measurementRepository;

    // Dependency Injection: Spring automatically provides the repository instance
    public MeasurementService(MeasurementRepository measurementRepository) {
        this.measurementRepository = measurementRepository;
    }

    /**
     * Saves a new Measurement record to the PostgreSQL database.
     * @param measurement The Measurement object to save.
     * @return The saved Measurement object (with its generated ID).
     */
    public Measurement saveMeasurement(Measurement measurement) {
        return measurementRepository.save(measurement);
    }

    /**
     * Retrieves the single most recent measurement from the database.
     * @return An Optional containing the latest Measurement, or empty if none exist.
     */
    public Optional<Measurement> getLatestMeasurement() {
        return measurementRepository.findTopByOrderByTimestampDesc();
    }

    /**
     * Calculates a simple Comfort Index based on temperature and humidity.
     * This is a core business logic method for the portfolio.
     * @param measurement The measurement data.
     * @return A descriptive string of the comfort status.
     */
    public String calculateComfortIndex(Measurement measurement) {
        Double temp = measurement.getTemperature();
        Double humid = measurement.getHumidity();

        if (temp == null || humid == null) {
            return "Data Incomplete";
        }

        // Ideal conditions: Temp between 20-24C and Humidity between 40-60%
        if (temp >= 20.0 && temp <= 24.0 && humid >= 40.0 && humid <= 60.0) {
            return "OPTIMAL 🟢";
        } else if (temp < 18.0 || temp > 28.0) {
            return "CRITICAL (Temp) 🔴";
        } else if (humid < 30.0 || humid > 70.0) {
            return "CRITICAL (Humidity) 🟠";
        } else {
            return "ACCEPTABLE 🟡";
        }
    }
}
package org.smarthome.controller;

import org.smarthome.model.Measurement;
import org.smarthome.service.MeasurementService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@RestController // Marks this class as a RESTful controller
@RequestMapping("/api/measurements") // Base URL for all endpoints in this controller
public class MeasurementController {

    private final MeasurementService measurementService;

    // Dependency Injection for the Service
    public MeasurementController(MeasurementService measurementService) {
        this.measurementService = measurementService;
    }

    /**
     * POST endpoint to receive sensor data from the hardware.
     * @param measurement The Measurement object received in the request body.
     * @return The saved Measurement object and HTTP 201 Created status.
     */
    @PostMapping
    public ResponseEntity<Measurement> receiveMeasurement(@RequestBody Measurement measurement) {
        Measurement savedMeasurement = measurementService.saveMeasurement(measurement);
        return ResponseEntity.status(201).body(savedMeasurement);
    }

    /**
     * GET endpoint to be polled by the HMI (STM32F7) to display current data.
     * @return The latest measurement data along with the calculated comfort index.
     */
    @GetMapping("/latest")
    public ResponseEntity<Map<String, Object>> getLatestMeasurement() {
        Optional<Measurement> latest = measurementService.getLatestMeasurement();

        if (latest.isEmpty()) {
            // Returns 404 Not Found if the database is empty
            return ResponseEntity.notFound().build();
        }

        Measurement measurement = latest.get();
        String comfortIndex = measurementService.calculateComfortIndex(measurement);

        // Build a custom response map for the HMI/Frontend
        Map<String, Object> response = new HashMap<>();
        response.put("data", measurement);
        response.put("comfortIndex", comfortIndex);

        return ResponseEntity.ok(response);
    }
}
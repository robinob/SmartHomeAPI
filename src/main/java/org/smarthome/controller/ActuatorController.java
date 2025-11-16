package org.smarthome.controller;

import org.smarthome.producer.ActuatorCommandProducer;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.Map;

@RestController // Marks this class as a RESTful controller
@RequestMapping("/api/actuator") // Base URL for actuator commands
public class ActuatorController {

    private final ActuatorCommandProducer producer;

    // Dependency Injection for the Producer Service
    public ActuatorController(ActuatorCommandProducer producer) {
        this.producer = producer;
    }

    /**
     * POST endpoint to receive actuator commands from the HMI.
     * The HMI sends a simple JSON object like: {"device": "FAN", "command": "ON"}.
     * This method then publishes the command to RabbitMQ.
     * @param payload A map containing the device and command strings.
     * @return 202 Accepted, indicating the command was successfully queued.
     */
    @PostMapping("/command")
    public ResponseEntity<String> sendCommand(@RequestBody Map<String, String> payload) {
        // 1. Extract command details from the incoming JSON payload
        String device = payload.get("device");
        String command = payload.get("command");

        if (device == null || command == null) {
            return ResponseEntity.badRequest().body("Payload must include 'device' and 'command'.");
        }

        // 2. Format the message payload (simple string for now)
        String commandMessage = String.format("%s_%s", device.toUpperCase(), command.toUpperCase());

        // 3. Publish the command asynchronously to the RabbitMQ queue
        producer.sendActuatorCommand(commandMessage);

        // 4. Return 202 Accepted
        // This is crucial: we return immediately without waiting for the STM32F4/PLC
        // to execute the command. The command is safely queued.
        return ResponseEntity.accepted().body("Command accepted and queued: " + commandMessage);
    }
}
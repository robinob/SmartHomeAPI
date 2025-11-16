package org.smarthome.producer;

import org.smarthome.config.RabbitMQConfig;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

@Service
public class ActuatorCommandProducer {

    // Spring's utility class for sending and receiving messages.
    private final RabbitTemplate rabbitTemplate;

    // Dependency Injection
    public ActuatorCommandProducer(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    /**
     * Publishes an actuator command to the RabbitMQ queue.
     * @param command A string representing the command (e.g., "FAN_ON", "LIGHT_OFF").
     */
    public void sendActuatorCommand(String command) {
        // We send the command as a simple string for now.
        // In a real system, this would be a JSON object with device ID and state.

        System.out.println("ActuatorCommandProducer: Sending command to RabbitMQ: " + command);

        rabbitTemplate.convertAndSend(
                RabbitMQConfig.EXCHANGE_NAME,
                RabbitMQConfig.ROUTING_KEY,
                command
        );

        System.out.println("ActuatorCommandProducer: Command sent successfully.");
    }
}
package org.smarthome.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

    // 1. Define Names (Constants)
    public static final String EXCHANGE_NAME = "actuator-exchange";
    public static final String ROUTING_KEY = "actuator.command";
    public static final String QUEUE_NAME = "actuator-command-queue";

    // 2. Define the Exchange
    // TopicExchange is flexible, allowing multiple routing keys.
    @Bean
    public TopicExchange exchange() {
        return new TopicExchange(EXCHANGE_NAME);
    }

    // 3. Define the Queue
    // This is where the message will sit until the STM32F4/PLC consumes it.
    @Bean
    public Queue queue() {
        return new Queue(QUEUE_NAME, false); // 'false' means the queue is not durable (for simplicity)
    }

    // 4. Bind the Queue to the Exchange
    // This tells RabbitMQ to send messages with the ROUTING_KEY to the QUEUE_NAME.
    @Bean
    public Binding binding(Queue queue, TopicExchange exchange) {
        return BindingBuilder.bind(queue).to(exchange).with(ROUTING_KEY);
    }
}
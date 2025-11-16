package org.smarthome;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SmartHomeApplication { // <--- The name should be updated here

    public static void main(String[] args) {
        SpringApplication.run(SmartHomeApplication.class, args);
    }
}
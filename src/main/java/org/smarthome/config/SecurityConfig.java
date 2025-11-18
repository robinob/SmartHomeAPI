package org.smarthome.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                // Disable CSRF because we are using a REST API (stateless)
                // and not a browser-based form application.
                .csrf(AbstractHttpConfigurer::disable)

                // Define Authorization Rules
                .authorizeHttpRequests(auth -> auth
                        // Allow public access to the HMI display endpoint (optional, but good for testing)
                        // .requestMatchers(HttpMethod.GET, "/api/measurements/latest").permitAll()

                        // Require authentication for everything else (Actuator commands, sensor data, Metrics)
                        .anyRequest().authenticated()
                )

                // Enable HTTP Basic Authentication (e.g., Pop-up in browser, header in API)
                .httpBasic(Customizer.withDefaults());

        return http.build();
    }

    @Bean
    public InMemoryUserDetailsManager userDetailsService() {
        // Create a hardcoded admin user for the portfolio MVP
        UserDetails user = User.withDefaultPasswordEncoder()
                .username("admin")
                .password("admin123")
                .roles("ADMIN")
                .build();

        return new InMemoryUserDetailsManager(user);
    }
}
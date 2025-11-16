package org.smarthome.repository;
import org.smarthome.model.Measurement;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;
// JpaRepository<Entity Class, ID Type>
public interface MeasurementRepository extends JpaRepository<Measurement, Long> {
    // Spring Data JPA automatically generates a query for this method name:
// SELECT * FROM measurements ORDER BY timestamp DESC LIMIT 1
    Optional<Measurement> findTopByOrderByTimestampDesc();
}
package ru.gofc.smart_home.hub.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.gofc.smart_home.hub.model.Sensor;

import java.util.Collection;
import java.util.Optional;

public interface SensorRepository extends JpaRepository<Sensor, String> {
    boolean existsByIdInAndHubId(Collection<String> ids, String hubId);

    Optional<Sensor> findByIdAndHubId(String id, String hubId);
}

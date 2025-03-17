package ru.gofc.smart_home.hub.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.gofc.smart_home.hub.model.Scenario;

import java.util.List;
import java.util.Optional;

public interface ScenarioRepository extends JpaRepository<Scenario, Long> {
    List<Scenario> findByHubId(String hubId);

    Optional<Scenario> findByHubIdAndName(String hubId, String name);

    void deleteByHubIdAndName(String hubId, String name);
}

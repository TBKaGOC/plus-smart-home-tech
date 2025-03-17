package ru.gofc.smart_home.hub.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.gofc.smart_home.hub.model.Condition;

public interface ConditionRepository extends JpaRepository<Condition, Long> {
}

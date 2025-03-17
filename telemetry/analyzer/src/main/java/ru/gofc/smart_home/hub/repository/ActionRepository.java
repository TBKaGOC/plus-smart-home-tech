package ru.gofc.smart_home.hub.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.gofc.smart_home.hub.model.Action;

public interface ActionRepository extends JpaRepository<Action, Long> {
}

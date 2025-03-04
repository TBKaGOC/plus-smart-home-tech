package ru.gofc.smart_home.hub.model;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import ru.gofc.smart_home.hub.model.enums.ConditionOperationType;
import ru.gofc.smart_home.hub.model.enums.ConditionType;

import java.util.List;

@Entity
@Table(name = "conditions")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Condition {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    @Column(nullable = false)
    ConditionType type;
    @Column(nullable = false)
    ConditionOperationType operation;
    @Column(nullable = false)
    Integer value;
    @ManyToOne(optional = false)
    @JoinTable(
            name = "scenario_conditions",
            joinColumns = @JoinColumn(name = "scenario_id"),
            inverseJoinColumns = @JoinColumn(name = "sensor_id")
    )
    Sensor conditionSource;
}

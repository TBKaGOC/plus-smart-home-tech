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
    @Column(nullable = false, name = "condition_value")
    Integer value;
    @ManyToOne(optional = false)
    @JoinColumn(name = "sensor_id")
    Sensor conditionSource;
}

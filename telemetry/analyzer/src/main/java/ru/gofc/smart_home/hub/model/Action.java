package ru.gofc.smart_home.hub.model;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import ru.gofc.smart_home.hub.model.enums.ActionType;

@Entity
@Table(name = "actions")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Action {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    @Column(nullable = false)
    ActionType type;
    @Column(nullable = false, name = "action_value")
    Integer value;
    @ManyToOne(optional = false)
    @JoinColumn(name = "sensor_id")
    Sensor actionPerformer;
}

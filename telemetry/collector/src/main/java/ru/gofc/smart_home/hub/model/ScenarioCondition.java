package ru.gofc.smart_home.hub.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.FieldDefaults;
import ru.gofc.smart_home.hub.model.enums.ConditionOperation;
import ru.gofc.smart_home.hub.model.enums.ConditionType;

@Getter
@Setter
@ToString
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ScenarioCondition {
    @NotBlank
    String sensorId;
    @NotNull
    ConditionType type;
    @NotNull
    ConditionOperation operation;
    Boolean value;
}

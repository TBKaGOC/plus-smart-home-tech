package ru.gofc.smart_home.hub.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.FieldDefaults;
import ru.gofc.smart_home.hub.model.enums.ActionType;

@Getter
@Setter
@ToString
@FieldDefaults(level = AccessLevel.PRIVATE)
public class DeviceAction {
    @NotBlank
    String sensorId;
    @NotNull
    ActionType type;
    Integer value;
}

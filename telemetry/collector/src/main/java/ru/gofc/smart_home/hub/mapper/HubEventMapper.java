package ru.gofc.smart_home.hub.mapper;

import ru.gofc.smart_home.hub.exception.HubMapException;
import ru.gofc.smart_home.hub.model.*;
import ru.yandex.practicum.kafka.telemetry.event.*;

import java.util.stream.Collectors;

public class HubEventMapper {
    private static DeviceAddedEventAvro mapToDeviceAddedAvro(DeviceAddedEvent event) {
        return DeviceAddedEventAvro.newBuilder()
                .setId(event.getId())
                .setType(DeviceTypeAvro.valueOf(event.getDeviceType().name()))
                .build();
    }

    private static DeviceRemovedEventAvro mapToDeviceRemovedAvro(DeviceRemovedEvent event) {
        return DeviceRemovedEventAvro.newBuilder()
                .setId(event.getId())
                .build();
    }

    private static ScenarioAddedEventAvro mapToScenarioAddedAvro(ScenarioAddedEvent event) {
        return ScenarioAddedEventAvro.newBuilder()
                .setName(event.getName())
                .setActions(
                        event.getActions().stream()
                                .map(HubEventMapper::mapToDeviceActionAvro)
                                .collect(Collectors.toList())
                )
                .setConditions(
                        event.getConditions().stream()
                                .map(HubEventMapper::mapToScenarioConditionAvro)
                                .collect(Collectors.toList())
                )
                .build();
    }

    private static ScenarioRemovedEventAvro mapToScenarioRemovedAvro(ScenarioRemovedEvent event) {
        return ScenarioRemovedEventAvro.newBuilder()
                .setName(event.getName())
                .build();
    }

    private static DeviceActionAvro mapToDeviceActionAvro(DeviceAction deviceAction) {
        return DeviceActionAvro.newBuilder()
                .setSensorId(deviceAction.getSensorId())
                .setValue(deviceAction.getValue())
                .setType(ActionTypeAvro.valueOf(deviceAction.getType().name())).build();
    }

    private static ScenarioConditionAvro mapToScenarioConditionAvro(ScenarioCondition condition) {
        return ScenarioConditionAvro.newBuilder()
                .setSensorId(condition.getSensorId())
                .setValue(condition.getValue())
                .setType(ConditionTypeAvro.valueOf(condition.getType().name()))
                .setOperation(ConditionOperationAvro.valueOf(condition.getOperation().name()))
                .build();
    }

    public static HubEventAvro mapToHubEventAvro(HubEvent event) throws HubMapException {
        return HubEventAvro.newBuilder()
                .setHubId(event.getHubId())
                .setTimestamp(event.getTimestamp())
                .setPayload(switchHubEventMapper(event))
                .build();
    }

    private static Object switchHubEventMapper(HubEvent event) throws HubMapException {
        if (event instanceof DeviceAddedEvent) {
            return mapToDeviceAddedAvro((DeviceAddedEvent) event);
        }
        if (event instanceof DeviceRemovedEvent) {
            return mapToDeviceRemovedAvro((DeviceRemovedEvent) event);
        }
        if (event instanceof ScenarioAddedEvent) {
            return mapToScenarioAddedAvro((ScenarioAddedEvent) event);
        }
        if (event instanceof ScenarioRemovedEvent) {
            return mapToScenarioRemovedAvro((ScenarioRemovedEvent) event);
        }

        throw new HubMapException("Такой тип события хаба не найден: " + event.getClass());
    }
}

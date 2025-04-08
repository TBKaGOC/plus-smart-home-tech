package ru.gofc.smart_home.hub.handler;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Component;
import ru.gofc.smart_home.hub.kafka.HubProducer;
import ru.yandex.practicum.grpc.telemetry.event.DeviceActionProto;
import ru.yandex.practicum.grpc.telemetry.event.HubEventProto;
import ru.yandex.practicum.grpc.telemetry.event.ScenarioAddedEventProto;
import ru.yandex.practicum.grpc.telemetry.event.ScenarioConditionProto;
import ru.yandex.practicum.kafka.telemetry.event.*;

import java.time.Instant;
import java.util.stream.Collectors;

@Component
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ScenarioAddedHandler implements HubHandler {
    final HubProducer producer;

    @Override
    public HubEventProto.PayloadCase getMessageType() {
        return HubEventProto.PayloadCase.SCENARIO_ADDED;
    }

    @Override
    public void handle(HubEventProto eventProto) {
        ScenarioAddedEventProto scenarioAddedEventProto = eventProto.getScenarioAdded();

        HubEventAvro eventAvro = HubEventAvro.newBuilder()
                .setHubId(eventProto.getHubId())
                .setTimestamp(Instant.ofEpochSecond(eventProto.getTimestamp().getSeconds(),
                        eventProto.getTimestamp().getNanos()))
                .setPayload(
                        ScenarioAddedEventAvro.newBuilder()
                                .setName(scenarioAddedEventProto.getName())
                                .setConditions(
                                        scenarioAddedEventProto.getConditionList().stream()
                                                .map(this::mapScenarioCondition)
                                                .collect(Collectors.toList())
                                )
                                .setActions(
                                        scenarioAddedEventProto.getActionList().stream()
                                                .map(this::mapDeviceAction)
                                                .collect(Collectors.toList())
                                )
                                .build()
                )
                .build();

        producer.sendMessage(eventAvro);
    }

    private ScenarioConditionAvro mapScenarioCondition(ScenarioConditionProto scenarioConditionProto) {
        ScenarioConditionAvro.Builder builder = ScenarioConditionAvro.newBuilder()
                .setSensorId(scenarioConditionProto.getSensorId())
                .setOperation(ConditionOperationAvro.valueOf(scenarioConditionProto.getOperation().name()))
                .setType(ConditionTypeAvro.valueOf(scenarioConditionProto.getType().name()));

        if (scenarioConditionProto.hasIntValue()) {
            builder.setValue(scenarioConditionProto.getIntValue());
        } else if (scenarioConditionProto.hasBoolValue()) {
            builder.setValue(scenarioConditionProto.getBoolValue() ? 1 : 0);
        }

        return builder.build();
    }

    private DeviceActionAvro mapDeviceAction(DeviceActionProto deviceActionProto) {
        return DeviceActionAvro.newBuilder()
                .setSensorId(deviceActionProto.getSensorId())
                .setValue(deviceActionProto.getValue())
                .setType(ActionTypeAvro.valueOf(deviceActionProto.getType().name()))
                .build();
    }
}

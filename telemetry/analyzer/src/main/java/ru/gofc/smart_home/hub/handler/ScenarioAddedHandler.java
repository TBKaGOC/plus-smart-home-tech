package ru.gofc.smart_home.hub.handler;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.apache.avro.specific.SpecificRecordBase;
import org.springframework.stereotype.Component;
import ru.gofc.smart_home.hub.model.Action;
import ru.gofc.smart_home.hub.model.Condition;
import ru.gofc.smart_home.hub.model.Scenario;
import ru.gofc.smart_home.hub.model.Sensor;
import ru.gofc.smart_home.hub.model.enums.ActionType;
import ru.gofc.smart_home.hub.model.enums.ConditionOperationType;
import ru.gofc.smart_home.hub.model.enums.ConditionType;
import ru.gofc.smart_home.hub.repository.ScenarioRepository;
import ru.gofc.smart_home.hub.repository.SensorRepository;
import ru.yandex.practicum.kafka.telemetry.event.DeviceActionAvro;
import ru.yandex.practicum.kafka.telemetry.event.HubEventAvro;
import ru.yandex.practicum.kafka.telemetry.event.ScenarioAddedEventAvro;
import ru.yandex.practicum.kafka.telemetry.event.ScenarioConditionAvro;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@FieldDefaults(level = AccessLevel.PRIVATE)
@Component
@AllArgsConstructor
@Slf4j
public class ScenarioAddedHandler implements HubEventHandler {
    final SensorRepository sensorRepository;
    final ScenarioRepository scenarioRepository;

    @Override
    public Class<? extends SpecificRecordBase> getType() {
        return ScenarioAddedEventAvro.class;
    }

    @Override
    public void handle(HubEventAvro hubEventAvro) {
        if (!(hubEventAvro.getPayload() instanceof ScenarioAddedEventAvro eventAvro)) {
            log.warn("Полученная сущность не является ScenarioAddedEventAvro");
            return;
        }
        log.info("Добавление сценария хаба " + hubEventAvro.getHubId());

        Scenario scenario = mapToScenario(eventAvro, hubEventAvro.getHubId());

        scenarioRepository.save(scenario);

        log.info("Добавлен сценарий хаба " + hubEventAvro.getHubId());
    }

    private Scenario mapToScenario(ScenarioAddedEventAvro eventAvro, String hubId) {
        Scenario scenario = new Scenario();
        scenario.setName(eventAvro.getName());
        scenario.setHubId(hubId);
        scenario.setActions(mapActions(eventAvro.getActions(), hubId));
        scenario.setConditions(mapConditions(eventAvro.getConditions(), hubId));

        return scenario;
    }

    private List<Action> mapActions(List<DeviceActionAvro> actions, String hubId) {
        List<Action> result = new ArrayList<>();

        for (DeviceActionAvro deviceAction: actions) {
            Optional<Sensor> performer = sensorRepository.findByIdAndHubId(deviceAction.getSensorId(), hubId);

            if (performer.isPresent()) {
                Action action = new Action();
                action.setType(ActionType.valueOf(deviceAction.getType().name()));
                action.setValue(deviceAction.getValue());
                action.setActionPerformer(performer.get());

                result.add(action);
            }
        }

        return result;
    }

    private List<Condition> mapConditions(List<ScenarioConditionAvro> conditions, String hubId) {
        List<Condition> result = new ArrayList<>();

        for (ScenarioConditionAvro scenarioCondition: conditions) {
            Optional<Sensor> source = sensorRepository.findByIdAndHubId(scenarioCondition.getSensorId(), hubId);

            if (source.isPresent()) {
                Condition condition = new Condition();
                condition.setType(ConditionType.valueOf(scenarioCondition.getType().name()));
                condition.setOperation(ConditionOperationType.valueOf(scenarioCondition.getOperation().name()));
                condition.setConditionSource(source.get());
                condition.setValue(scenarioCondition.getValue());

                result.add(condition);
            }
        }

        return result;
    }
}

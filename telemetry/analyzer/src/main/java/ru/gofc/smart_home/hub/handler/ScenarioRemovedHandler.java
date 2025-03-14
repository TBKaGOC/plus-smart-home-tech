package ru.gofc.smart_home.hub.handler;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.apache.avro.specific.SpecificRecordBase;
import org.springframework.stereotype.Component;
import ru.gofc.smart_home.hub.repository.ScenarioRepository;
import ru.yandex.practicum.kafka.telemetry.event.HubEventAvro;
import ru.yandex.practicum.kafka.telemetry.event.ScenarioRemovedEventAvro;

@FieldDefaults(level = AccessLevel.PRIVATE)
@Component
@AllArgsConstructor
@Slf4j
public class ScenarioRemovedHandler extends HubEventHandler<ScenarioRemovedEventAvro> {
    final ScenarioRepository repository;

    @Override
    public Class<ScenarioRemovedEventAvro> getType() {
        return ScenarioRemovedEventAvro.class;
    }

    @Override
    public void handle(HubEventAvro hubEventAvro) {
        ScenarioRemovedEventAvro eventAvro = instance(hubEventAvro.getPayload(), ScenarioRemovedEventAvro.class);

        if (eventAvro != null) {
            log.info("Удаление сценария хаба " + hubEventAvro.getHubId());

            repository.deleteByHubIdAndName(hubEventAvro.getHubId(), eventAvro.getName());

            log.info("Удалён сценарий хаба " + hubEventAvro.getHubId());
        }
    }
}

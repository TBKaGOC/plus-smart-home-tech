package ru.gofc.smart_home.hub.handler;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.apache.avro.specific.SpecificRecordBase;
import org.springframework.stereotype.Component;
import ru.gofc.smart_home.hub.repository.SensorRepository;
import ru.yandex.practicum.kafka.telemetry.event.DeviceRemovedEventAvro;
import ru.yandex.practicum.kafka.telemetry.event.HubEventAvro;

@FieldDefaults(level = AccessLevel.PRIVATE)
@Component
@AllArgsConstructor
@Slf4j
public class DeviceRemovedHandler extends HubEventHandler<DeviceRemovedEventAvro> {
    final SensorRepository repository;
    @Override
    public Class<DeviceRemovedEventAvro> getType() {
        return DeviceRemovedEventAvro.class;
    }

    @Override
    public void handle(HubEventAvro hubEventAvro) {
        DeviceRemovedEventAvro eventAvro = instance(hubEventAvro.getPayload(), DeviceRemovedEventAvro.class);

        if (eventAvro != null) {
            log.info("Удаление датчика " + eventAvro.getId());

            repository.deleteById(eventAvro.getId());

            log.info("Удалён датчик " + eventAvro.getId());
        }
    }
}

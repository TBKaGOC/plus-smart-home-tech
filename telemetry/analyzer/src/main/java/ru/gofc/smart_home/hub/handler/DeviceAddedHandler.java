package ru.gofc.smart_home.hub.handler;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.apache.avro.specific.SpecificRecordBase;
import org.springframework.stereotype.Component;
import ru.gofc.smart_home.hub.model.Sensor;
import ru.gofc.smart_home.hub.repository.SensorRepository;
import ru.yandex.practicum.kafka.telemetry.event.DeviceAddedEventAvro;
import ru.yandex.practicum.kafka.telemetry.event.HubEventAvro;

@FieldDefaults(level = AccessLevel.PRIVATE)
@Component
@AllArgsConstructor
@Slf4j
public class DeviceAddedHandler extends HubEventHandler<DeviceAddedEventAvro> {
    final SensorRepository repository;

    @Override
    public Class<DeviceAddedEventAvro> getType() {
        return DeviceAddedEventAvro.class;
    }

    @Override
    public void handle(HubEventAvro hubEventAvro) {
        DeviceAddedEventAvro eventAvro = instance(hubEventAvro.getPayload(), DeviceAddedEventAvro.class);

        if (eventAvro != null) {
            log.info("Добавление датчика" + eventAvro.getId());

            repository.save(new Sensor(eventAvro.getId(), hubEventAvro.getHubId()));

            log.info("Добавлен датчик " + eventAvro.getId());
        }
    }
}

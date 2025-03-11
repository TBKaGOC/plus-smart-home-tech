package ru.gofc.smart_home.snapshot.handler;

import ru.yandex.practicum.kafka.telemetry.event.SensorEventAvro;
import ru.yandex.practicum.kafka.telemetry.event.SensorStateAvro;
import ru.yandex.practicum.kafka.telemetry.event.SensorsSnapshotAvro;

import java.time.Instant;
import java.util.Map;
import java.util.Optional;

public abstract class SensorHandler<T> {
    public abstract Class<T> getMessageType();

    Optional<Map<String, SensorStateAvro>> handle(SensorEventAvro eventAvro, SensorsSnapshotAvro snapshot) {
        T event = (T) eventAvro.getPayload();

        Map<String, SensorStateAvro> states = snapshot.getSensorsState();
        SensorStateAvro deviceSnapshot;

        if (!states.isEmpty() && states.containsKey(eventAvro.getId())) {
            deviceSnapshot = states.get(eventAvro.getId());

            T oldEvent = (T) deviceSnapshot.getData();

            if (eventAvro.getTimestamp().isBefore(deviceSnapshot.getTimestamp())) {
                return Optional.empty();
            }
            if (event.equals(oldEvent)) {
                return Optional.empty();
            }
        } else {
            deviceSnapshot = new SensorStateAvro();
        }
        deviceSnapshot.setTimestamp(eventAvro.getTimestamp());
        deviceSnapshot.setData(event);

        states.put(eventAvro.getId(), deviceSnapshot);

        return Optional.of(states);
    }
}

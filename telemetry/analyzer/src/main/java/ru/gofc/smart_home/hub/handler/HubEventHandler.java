package ru.gofc.smart_home.hub.handler;

import org.apache.avro.specific.SpecificRecordBase;
import ru.yandex.practicum.kafka.telemetry.event.HubEventAvro;

public interface HubEventHandler {
    Class<? extends SpecificRecordBase> getType();

    void handle(HubEventAvro hubEventAvro);
}

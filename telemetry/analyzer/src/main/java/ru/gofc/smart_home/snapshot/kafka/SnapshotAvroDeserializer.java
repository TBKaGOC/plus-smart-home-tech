package ru.gofc.smart_home.snapshot.kafka;

import org.apache.avro.Schema;
import ru.gofc.smart_home.hub.kafka.BaseAvroDeserializer;
import ru.yandex.practicum.kafka.telemetry.event.SensorsSnapshotAvro;

public class SnapshotAvroDeserializer extends BaseAvroDeserializer<SensorsSnapshotAvro> {
    public SnapshotAvroDeserializer() {
        super(SensorsSnapshotAvro.getClassSchema());
    }

    public SnapshotAvroDeserializer(Schema schema) {
        super(schema);
    }
}

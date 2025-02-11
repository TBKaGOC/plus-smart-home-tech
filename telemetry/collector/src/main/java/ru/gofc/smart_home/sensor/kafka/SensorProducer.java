package ru.gofc.smart_home.sensor.kafka;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.kafka.telemetry.event.SensorEventAvro;
import ru.gofc.smart_home.sensor.exception.SensorMapException;
import ru.gofc.smart_home.sensor.mapper.SensorEventMapper;
import ru.gofc.smart_home.sensor.model.SensorEvent;

@Component
@FieldDefaults(level = AccessLevel.PRIVATE)
@AllArgsConstructor
public class SensorProducer {
    Producer<String, SensorEventAvro> producer;
    final String topic;

    public void sendMessage(SensorEvent event) throws SensorMapException {
        SensorEventAvro eventAvro = SensorEventMapper.mapToSensorEventAvro(event);
        ProducerRecord<String, SensorEventAvro> producerRecord = new ProducerRecord<>(topic, eventAvro);

        producer.send(producerRecord);
        producer.flush();
    }
}

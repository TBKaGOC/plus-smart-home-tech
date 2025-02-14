package ru.gofc.smart_home.sensor.kafka;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.kafka.telemetry.event.SensorEventAvro;
import ru.gofc.smart_home.sensor.mapper.SensorEventMapper;
import ru.gofc.smart_home.sensor.model.SensorEvent;

@FieldDefaults(level = AccessLevel.PRIVATE)
@AllArgsConstructor
@Slf4j
public class SensorProducer {
    Producer<String, SensorEventAvro> producer;
    final String topic;

    public SensorEvent sendMessage(SensorEvent event) {
        log.info("Отправление события сенсора " + event.getId());
        log.debug("Отправление события сенсора " + event);

        SensorEventAvro eventAvro = SensorEventMapper.mapToSensorEventAvro(event);
        ProducerRecord<String, SensorEventAvro> producerRecord = new ProducerRecord<>(topic, eventAvro);

        producer.send(producerRecord);
        producer.flush();

        log.info("Успешно отправлено событие сенсора " + event.getId());

        return event;
    }
}

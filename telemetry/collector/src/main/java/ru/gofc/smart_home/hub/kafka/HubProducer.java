package ru.gofc.smart_home.hub.kafka;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.stereotype.Component;
import ru.gofc.smart_home.hub.mapper.HubEventMapper;
import ru.gofc.smart_home.hub.model.HubEvent;
import ru.yandex.practicum.kafka.telemetry.event.HubEventAvro;

@FieldDefaults(level = AccessLevel.PRIVATE)
@AllArgsConstructor
@Slf4j
public class HubProducer {
    Producer<String, HubEventAvro> producer;
    final String topic;

    public HubEvent sendMessage(HubEvent event) {
        log.info("Отправление события хаба " + event.getHubId());
        log.debug("Отправление события хаба " + event);

        HubEventAvro avro = HubEventMapper.mapToHubEventAvro(event);
        ProducerRecord<String, HubEventAvro> producerRecord = new ProducerRecord<>(topic, avro);

        producer.send(producerRecord);
        producer.flush();

        log.info("Успешно завершилось событие хаба " + event.getHubId());

        return event;
    }
}

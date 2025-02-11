package ru.gofc.smart_home.hub.kafka;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.stereotype.Component;
import ru.gofc.smart_home.hub.exception.HubMapException;
import ru.gofc.smart_home.hub.mapper.HubEventMapper;
import ru.gofc.smart_home.hub.model.HubEvent;
import ru.yandex.practicum.kafka.telemetry.event.HubEventAvro;

@Component
@FieldDefaults(level = AccessLevel.PRIVATE)
@AllArgsConstructor
public class HubProducer {
    Producer<String, HubEventAvro> producer;
    final String topic;

    public void sendMessage(HubEvent event) throws HubMapException {
        HubEventAvro avro = HubEventMapper.mapToHubEventAvro(event);
        ProducerRecord<String, HubEventAvro> producerRecord = new ProducerRecord<>(topic, avro);

        producer.send(producerRecord);
        producer.flush();
    }
}

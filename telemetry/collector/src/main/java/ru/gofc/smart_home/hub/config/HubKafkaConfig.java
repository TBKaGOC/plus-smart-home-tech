package ru.gofc.smart_home.hub.config;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.VoidSerializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.gofc.smart_home.hub.kafka.HubEventSerializer;
import ru.gofc.smart_home.hub.kafka.HubProducer;
import ru.yandex.practicum.kafka.telemetry.event.HubEventAvro;

import java.util.Properties;

@Configuration
public class HubKafkaConfig {
    @Bean
    public HubProducer hubProducerConfig() {
        Properties properties = new Properties();
        properties.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        properties.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, VoidSerializer.class);
        properties.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, HubEventSerializer.class);

        Producer<String, HubEventAvro> producer = new KafkaProducer<>(properties);

        return new HubProducer(producer, "telemetry.hubs.v1");
    }
}

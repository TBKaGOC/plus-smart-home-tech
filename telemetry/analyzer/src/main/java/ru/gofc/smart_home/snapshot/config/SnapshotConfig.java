package ru.gofc.smart_home.snapshot.config;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.serialization.VoidDeserializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import ru.gofc.smart_home.snapshot.SnapshotProcessor;
import ru.gofc.smart_home.snapshot.handle.SnapshotHandler;
import ru.gofc.smart_home.snapshot.kafka.SnapshotAvroDeserializer;
import ru.yandex.practicum.kafka.telemetry.event.SensorsSnapshotAvro;

import java.util.List;
import java.util.Properties;

@ConfigurationProperties("kafka.constants")
@AllArgsConstructor
public class SnapshotConfig {
    private final String url;
    private final Topic snapshot;

    @Bean
    public SnapshotProcessor snapshotProcessor(SnapshotHandler handler) {
        Properties properties = new Properties();
        properties.put(ConsumerConfig.GROUP_ID_CONFIG, "snapshot");
        properties.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");

        properties.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, url);
        properties.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, VoidDeserializer.class);
        properties.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, SnapshotAvroDeserializer.class);

        Consumer<String, SensorsSnapshotAvro> consumer = new KafkaConsumer<>(properties);
        consumer.subscribe(List.of(snapshot.getTopic()));

        return new SnapshotProcessor(consumer, handler);
    }

    @AllArgsConstructor
    @Getter
    @Setter
    private static class Topic {
        private String topic;
    }
}

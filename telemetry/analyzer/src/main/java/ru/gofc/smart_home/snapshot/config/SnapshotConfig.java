package ru.gofc.smart_home.snapshot.config;

import lombok.AllArgsConstructor;
import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.serialization.VoidDeserializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import ru.gofc.smart_home.snapshot.SnapshotProcessor;
import ru.gofc.smart_home.snapshot.handle.SnapshotHandler;
import ru.gofc.smart_home.snapshot.kafka.SnapshotAvroDeserializer;
import ru.yandex.practicum.kafka.telemetry.event.SensorsSnapshotAvro;

import java.util.List;
import java.util.Properties;

@Configuration
@PropertySource("classpath:application.yaml")
public class SnapshotConfig {
    private final String url;
    private final String topic;

    public SnapshotConfig(
            @Value("${kafka.constants.url}") String url,
            @Value("${kafka.constants.snapshot.topic}") String topic
    ) {
        this.url = url;
        this.topic = topic;
    }

    @Bean
    public SnapshotProcessor snapshotProcessor(SnapshotHandler handler) {
        Properties properties = new Properties();
        properties.put(ConsumerConfig.GROUP_ID_CONFIG, "snapshot");
        properties.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");

        properties.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, url);
        properties.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, VoidDeserializer.class);
        properties.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, SnapshotAvroDeserializer.class);

        Consumer<String, SensorsSnapshotAvro> consumer = new KafkaConsumer<>(properties);
        consumer.subscribe(List.of(topic));

        return new SnapshotProcessor(consumer, handler);
    }
}

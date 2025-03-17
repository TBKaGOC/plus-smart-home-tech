package ru.gofc.smart_home.hub.config;

import lombok.AllArgsConstructor;
import org.apache.avro.specific.SpecificRecord;
import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.serialization.VoidDeserializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.gofc.smart_home.hub.HubEventProcessor;
import ru.gofc.smart_home.hub.handler.HubEventHandler;
import ru.gofc.smart_home.hub.kafka.HubEventDeserializer;
import ru.yandex.practicum.kafka.telemetry.event.HubEventAvro;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@ConfigurationProperties("kafka.constants")
public class HubEventConfig {
    private final String url;
    private final String topic;

    public HubEventConfig(
            @Value("${url}") String url,
            @Value("${hub.topic}") String topic
    ) {
        this.url = url;
        this.topic = topic;
    }

    @Bean
    public HubEventProcessor hubEventProcessor(Set<HubEventHandler> handlers) {
        Properties properties = new Properties();
        properties.put(ConsumerConfig.GROUP_ID_CONFIG, "hub");
        properties.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");

        properties.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, url);
        properties.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, VoidDeserializer.class);
        properties.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, HubEventDeserializer.class);

        Consumer<String, HubEventAvro> consumer = new KafkaConsumer<>(properties);
        consumer.subscribe(List.of(topic));

        return new HubEventProcessor(getHandlers(handlers), consumer);
    }

    private Map<Class<? extends SpecificRecord>, HubEventHandler> getHandlers(Set<HubEventHandler> handlers) {
        return handlers.stream()
                .collect(Collectors.toMap(
                        HubEventHandler::getType,
                        Function.identity()
                ));
    }
}

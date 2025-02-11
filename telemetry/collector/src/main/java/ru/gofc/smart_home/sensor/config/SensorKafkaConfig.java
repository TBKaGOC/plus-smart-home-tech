package ru.gofc.smart_home.sensor.config;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.VoidSerializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.yandex.practicum.kafka.telemetry.event.SensorEventAvro;
import ru.gofc.smart_home.sensor.kafka.SensorProducer;
import ru.gofc.smart_home.sensor.kafka.SensorEventSerializer;

import java.util.Properties;

@Configuration
public class SensorKafkaConfig {
    @Bean
    public SensorProducer sensorProducerConfig() {
        Properties properties = new Properties();
        properties.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        properties.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, VoidSerializer.class);
        properties.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, SensorEventSerializer.class);

        Producer<String, SensorEventAvro> producer = new KafkaProducer<>(properties);

        return new SensorProducer(producer, "telemetry.sensors.v1");
    }
}

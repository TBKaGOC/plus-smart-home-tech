package ru.gofc.smart_home.hub;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.apache.avro.specific.SpecificRecord;
import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.springframework.stereotype.Component;
import ru.gofc.smart_home.hub.handler.HubEventHandler;
import ru.yandex.practicum.kafka.telemetry.event.HubEventAvro;

import java.time.Duration;
import java.util.Map;

@FieldDefaults(level = AccessLevel.PRIVATE)
@AllArgsConstructor
@Component
@Slf4j
public class HubEventProcessor implements Runnable {
    final Map<Class<? extends SpecificRecord>, HubEventHandler> handlers;
    final Consumer<String, HubEventAvro> consumer;

    @Override
    public void run() {
        try {
            log.info("Получение данных");

            while (true) {
                ConsumerRecords<String, HubEventAvro> records = consumer.poll(Duration.ofMillis(500));

                for (ConsumerRecord<String, HubEventAvro> record : records) {
                    HubEventAvro value = record.value();

                    if (handlers.containsKey(value.getPayload().getClass())) {
                        handlers.get(value.getPayload().getClass()).handle(value);
                    } else {
                        throw new IllegalArgumentException("Не найден обработчик " + value.getPayload().getClass());
                    }
                }
            }
        }  catch (Exception e) {
            log.error("Сбой обработки события", e);
        } finally {
            try {
                consumer.commitSync();
            } finally {
                log.info("Закрываем консьюмер");
                consumer.close();
            }
        }
    }
}

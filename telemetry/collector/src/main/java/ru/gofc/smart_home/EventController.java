package ru.gofc.smart_home;

import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.gofc.smart_home.hub.exception.HubMapException;
import ru.gofc.smart_home.hub.kafka.HubProducer;
import ru.gofc.smart_home.hub.model.HubEvent;
import ru.gofc.smart_home.sensor.exception.SensorMapException;
import ru.gofc.smart_home.sensor.model.SensorEvent;
import ru.gofc.smart_home.sensor.kafka.SensorProducer;

@RestController
@RequestMapping("/events")
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class EventController {
    SensorProducer sensorProducer;
    HubProducer hubProducer;

    @PostMapping("/sensors")
    public void postSensorEvent(@Valid @RequestBody SensorEvent event) throws SensorMapException {
        sensorProducer.sendMessage(event);
    }

    @PostMapping("/hub")
    public void postHubEvent(@Valid @RequestBody HubEvent event) throws HubMapException {
        hubProducer.sendMessage(event);
    }
}

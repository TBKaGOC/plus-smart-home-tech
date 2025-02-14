package ru.gofc.smart_home;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import ru.gofc.smart_home.exception.EventErrorHandler;
import ru.gofc.smart_home.hub.kafka.HubProducer;
import ru.gofc.smart_home.hub.model.*;
import ru.gofc.smart_home.hub.model.enums.DeviceType;
import ru.gofc.smart_home.sensor.kafka.SensorProducer;
import ru.gofc.smart_home.sensor.model.*;

import java.nio.charset.StandardCharsets;
import java.util.List;

import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
public class EventControllerTests {
    @Mock
    private SensorProducer sensorProducer;
    @Mock
    private HubProducer hubProducer;
    @InjectMocks
    private EventController controller;
    private final ObjectMapper mapper = JsonMapper.builder()
            .findAndAddModules()
            .build();
    private MockMvc mvc;

    @BeforeEach
    void setUp() {
        mvc = MockMvcBuilders
                .standaloneSetup(controller)
                .setControllerAdvice(EventErrorHandler.class)
                .build();
    }

    @Test
    void postClimateSensorEvent() throws Exception {
        ClimateSensorEvent event = new ClimateSensorEvent();
        event.setId("1");
        event.setHumidity(1);
        event.setTemperatureC(1);
        event.setCo2Level(1);
        event.setHubId("1");

        when(sensorProducer.sendMessage(any()))
                .thenReturn(event);

        mvc.perform(post("/events/sensors").content(mapper.writeValueAsString(event))
                        .characterEncoding(StandardCharsets.UTF_8)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(event.getId()), String.class))
                .andExpect(jsonPath("$.humidity", is(event.getHumidity()), Integer.class))
                .andExpect(jsonPath("$.co2Level", is(event.getCo2Level()), Integer.class))
                .andExpect(jsonPath("$.temperatureC", is(event.getTemperatureC()), Integer.class))
                .andExpect(jsonPath("$.hubId", is(event.getHubId()), String.class));
    }

    @Test
    void postLightSensorEvent() throws Exception {
        LightSensorEvent event = new LightSensorEvent();
        event.setId("1");
        event.setHubId("1");
        event.setLinkQuality(1);
        event.setLuminosity(1);

        when(sensorProducer.sendMessage(any()))
                .thenReturn(event);

        mvc.perform(post("/events/sensors").content(mapper.writeValueAsString(event))
                        .characterEncoding(StandardCharsets.UTF_8)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(event.getId()), String.class))
                .andExpect(jsonPath("$.linkQuality", is(event.getLinkQuality()), Integer.class))
                .andExpect(jsonPath("$.luminosity", is(event.getLuminosity()), Integer.class))
                .andExpect(jsonPath("$.hubId", is(event.getHubId()), String.class));
    }

    @Test
    void postMotionSensorEvent() throws Exception {
        MotionSensorEvent event = new MotionSensorEvent();
        event.setId("1");
        event.setHubId("1");
        event.setVoltage(1);
        event.setLinkQuality(1);
        event.setMotion(true);

        when(sensorProducer.sendMessage(any()))
                .thenReturn(event);

        mvc.perform(post("/events/sensors").content(mapper.writeValueAsString(event))
                        .characterEncoding(StandardCharsets.UTF_8)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(event.getId()), String.class))
                .andExpect(jsonPath("$.linkQuality", is(event.getLinkQuality()), Integer.class))
                .andExpect(jsonPath("$.voltage", is(event.getVoltage()), Integer.class))
                .andExpect(jsonPath("$.motion", is(event.getMotion()), Boolean.class))
                .andExpect(jsonPath("$.hubId", is(event.getHubId()), String.class));
    }

    @Test
    void postSwitchSensorEvent() throws Exception {
        SwitchSensorEvent event = new SwitchSensorEvent();
        event.setId("1");
        event.setHubId("1");
        event.setState(true);

        when(sensorProducer.sendMessage(any()))
                .thenReturn(event);

        mvc.perform(post("/events/sensors").content(mapper.writeValueAsString(event))
                        .characterEncoding(StandardCharsets.UTF_8)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(event.getId()), String.class))
                .andExpect(jsonPath("$.state", is(event.getState()), Boolean.class))
                .andExpect(jsonPath("$.hubId", is(event.getHubId()), String.class));
    }

    @Test
    void postTemperatureSensorEvent() throws Exception {
        TemperatureSensorEvent event = new TemperatureSensorEvent();
        event.setId("1");
        event.setHubId("1");
        event.setTemperatureC(1);
        event.setTemperatureF(1);

        when(sensorProducer.sendMessage(any()))
                .thenReturn(event);

        mvc.perform(post("/events/sensors").content(mapper.writeValueAsString(event))
                        .characterEncoding(StandardCharsets.UTF_8)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(event.getId()), String.class))
                .andExpect(jsonPath("$.temperatureC", is(event.getTemperatureC()), Integer.class))
                .andExpect(jsonPath("$.temperatureF", is(event.getTemperatureF()), Integer.class))
                .andExpect(jsonPath("$.hubId", is(event.getHubId()), String.class));
    }

    @Test
    void postDeviceAddedEvent() throws Exception {
        DeviceAddedEvent event = new DeviceAddedEvent();
        event.setId("1");
        event.setHubId("1");
        event.setDeviceType(DeviceType.SWITCH_SENSOR);

        when(hubProducer.sendMessage(any()))
                .thenReturn(event);

        mvc.perform(post("/events/hub").content(mapper.writeValueAsString(event))
                        .characterEncoding(StandardCharsets.UTF_8)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(event.getId()), String.class))
                .andExpect(jsonPath("$.deviceType", is(event.getDeviceType().name()), String.class))
                .andExpect(jsonPath("$.hubId", is(event.getHubId()), String.class));
    }

    @Test
    void postDeviceDeletedEvent() throws Exception {
        DeviceRemovedEvent event = new DeviceRemovedEvent();
        event.setId("1");
        event.setHubId("1");

        when(hubProducer.sendMessage(any()))
                .thenReturn(event);

        mvc.perform(post("/events/hub").content(mapper.writeValueAsString(event))
                        .characterEncoding(StandardCharsets.UTF_8)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(event.getId()), String.class))
                .andExpect(jsonPath("$.hubId", is(event.getHubId()), String.class));
    }

    @Test
    void postScenarioAddedEvent() throws Exception {
        ScenarioAddedEvent event = new ScenarioAddedEvent();
        event.setHubId("1");
        event.setName("name");
        event.setConditions(List.of(new ScenarioCondition()));
        event.setActions(List.of(new DeviceAction()));

        when(hubProducer.sendMessage(any()))
                .thenReturn(event);

        mvc.perform(post("/events/hub").content(mapper.writeValueAsString(event))
                        .characterEncoding(StandardCharsets.UTF_8)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is(event.getName()), String.class))
                .andExpect(jsonPath("$.hubId", is(event.getHubId()), String.class));
    }

    @Test
    void postScenarioDeletedEvent() throws Exception {
        ScenarioRemovedEvent event = new ScenarioRemovedEvent();
        event.setHubId("1");
        event.setName("name");

        when(hubProducer.sendMessage(any()))
                .thenReturn(event);

        mvc.perform(post("/events/hub").content(mapper.writeValueAsString(event))
                        .characterEncoding(StandardCharsets.UTF_8)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is(event.getName()), String.class))
                .andExpect(jsonPath("$.hubId", is(event.getHubId()), String.class));
    }
}

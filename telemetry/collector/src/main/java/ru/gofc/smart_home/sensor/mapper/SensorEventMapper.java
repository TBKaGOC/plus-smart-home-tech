package ru.gofc.smart_home.sensor.mapper;

import ru.yandex.practicum.kafka.telemetry.event.*;
import ru.gofc.smart_home.sensor.model.*;

public class SensorEventMapper {
    public static ClimateSensorAvro mapToClimateAvro(ClimateSensorEvent event) {
        return ClimateSensorAvro.newBuilder()
                .setCo2Level(event.getCo2Level())
                .setHumidity(event.getHumidity())
                .setTemperatureC(event.getTemperatureC())
                .build();
    }
    public static LightSensorAvro mapToLightAvro(LightSensorEvent event) {
        return LightSensorAvro.newBuilder()
                .setLinkQuality(event.getLinkQuality())
                .setLuminosity(event.getLuminosity())
                .build();
    }

    public static MotionSensorAvro mapToMotionAvro(MotionSensorEvent event) {
        return MotionSensorAvro.newBuilder()
                .setLinkQuality(event.getLinkQuality())
                .setMotion(event.getMotion())
                .setVoltage(event.getVoltage())
                .build();
    }

    public static SwitchSensorAvro mapToSwitchAvro(SwitchSensorEvent event) {
        return SwitchSensorAvro.newBuilder()
                .setState(event.getState())
                .build();
    }

    public static TemperatureSensorAvro mapToTemperatureAvro(TemperatureSensorEvent event) {
        return TemperatureSensorAvro.newBuilder()
                .setTemperatureC(event.getTemperatureC())
                .setTemperatureF(event.getTemperatureF())
                .build();
    }

    public static SensorEventAvro mapToSensorEventAvro(SensorEvent event) {
        return SensorEventAvro.newBuilder()
                .setId(event.getId())
                .setHubId(event.getHubId())
                .setTimestamp(event.getTimestamp())
                .setPayload(event.getType().switchSensorMapper(event))
                .build();
    }
}

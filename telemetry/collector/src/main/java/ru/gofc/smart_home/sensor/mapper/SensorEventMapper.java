package ru.gofc.smart_home.sensor.mapper;

import ru.yandex.practicum.kafka.telemetry.event.*;
import ru.gofc.smart_home.sensor.exception.SensorMapException;
import ru.gofc.smart_home.sensor.model.*;

public class SensorEventMapper {
    private static ClimateSensorAvro mapToClimateAvro(ClimateSensorEvent event) {
        return ClimateSensorAvro.newBuilder()
                .setCo2Level(event.getCo2Level())
                .setHumidity(event.getHumidity())
                .setTemperatureC(event.getTemperatureC())
                .build();
    }
    private static LightSensorAvro mapToLightAvro(LightSensorEvent event) {
        return LightSensorAvro.newBuilder()
                .setLinkQuality(event.getLinkQuality())
                .setLuminosity(event.getLuminosity())
                .build();
    }

    private static MotionSensorAvro mapToMotionAvro(MotionSensorEvent event) {
        return MotionSensorAvro.newBuilder()
                .setLinkQuality(event.getLinkQuality())
                .setMotion(event.getMotion())
                .setVoltage(event.getVoltage())
                .build();
    }

    private static SwitchSensorAvro mapToSwitchAvro(SwitchSensorEvent event) {
        return SwitchSensorAvro.newBuilder()
                .setState(event.getState())
                .build();
    }

    private static TemperatureSensorAvro mapToTemperatureAvro(TemperatureSensorEvent event) {
        return TemperatureSensorAvro.newBuilder()
                .setTemperatureC(event.getTemperatureC())
                .setTemperatureF(event.getTemperatureF())
                .build();
    }

    public static SensorEventAvro mapToSensorEventAvro(SensorEvent event) throws SensorMapException {
        return SensorEventAvro.newBuilder()
                .setId(event.getId())
                .setHubId(event.getHubId())
                .setTimestamp(event.getTimestamp())
                .setPayload(switchSensorAvroMapper(event))
                .build();
    }

    private static Object switchSensorAvroMapper(SensorEvent event) throws SensorMapException {
        if (event instanceof ClimateSensorEvent) {
            return mapToClimateAvro((ClimateSensorEvent) event);
        }
        if (event instanceof LightSensorEvent) {
            return mapToLightAvro((LightSensorEvent) event);
        }
        if (event instanceof MotionSensorEvent) {
            return mapToMotionAvro((MotionSensorEvent) event);
        }
        if (event instanceof SwitchSensorEvent) {
            return mapToSwitchAvro((SwitchSensorEvent) event);
        }
        if (event instanceof TemperatureSensorEvent) {
            return mapToTemperatureAvro((TemperatureSensorEvent) event);
        }

        throw new SensorMapException("Такой тип сенсора не найден: " + event.getClass());
    }
}

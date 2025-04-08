package ru.gofc.smart_home.hub.model.enums;

import ru.yandex.practicum.kafka.telemetry.event.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

public enum ConditionType {
    MOTION {
        @Override
        public List<Integer> cast(SensorStateAvro state) {
            if (state == null) return new ArrayList<>();
            if (state.getData() instanceof MotionSensorAvro sensor) {
                return Collections.singletonList(sensor.getMotion() ? 1 : 0);
            } else {
                throw new IllegalArgumentException("Переданная сущность не является MotionSensorAvro " +
                        state.getClass());
            }
        }
    },
    LUMINOSITY {
        @Override
        public List<Integer> cast(SensorStateAvro state) {
            if (state == null) return new ArrayList<>();
            if (state.getData() instanceof LightSensorAvro sensor) {
                return Collections.singletonList(sensor.getLuminosity());
            } else {
                throw new IllegalArgumentException("Переданная сущность не является LightSensorAvro " +
                        state.getClass());
            }
        }
    },
    SWITCH {
        @Override
        public List<Integer> cast(SensorStateAvro state) {
            if (state == null) return new ArrayList<>();
            if (state.getData() instanceof SwitchSensorAvro sensor) {
                return Collections.singletonList(sensor.getState() ? 1 : 0);
            } else {
                throw new IllegalArgumentException("Переданная сущность не является SwitchSensorAvro " +
                        state.getClass());
            }
        }
    },
    TEMPERATURE {
        @Override
        public List<Integer> cast(SensorStateAvro state) {
            if (state == null) return new ArrayList<>();
            if (state.getData() instanceof TemperatureSensorAvro sensor) {
                return List.of(sensor.getTemperatureC(), sensor.getTemperatureF());
            } if (state.getData() instanceof ClimateSensorAvro sensor) {
                return Collections.singletonList(sensor.getTemperatureC());
            } else {
                throw new IllegalArgumentException("Переданная сущность не является TemperatureSensorAvro " +
                        "или ClimateSensorAvro " + state.getClass());
            }
        }
    },
    CO2LEVEL {
        @Override
        public List<Integer> cast(SensorStateAvro state) {
            if (state == null) return new ArrayList<>();
            if (state.getData() instanceof ClimateSensorAvro sensor) {
                return Collections.singletonList(sensor.getCo2Level());
            } else {
                throw new IllegalArgumentException("Переданная сущность не является ClimateSensorAvro " +
                        state.getClass());
            }
        }
    },
    HUMIDITY {
        @Override
        public List<Integer> cast(SensorStateAvro state) {
            if (state == null) return new ArrayList<>();
            if (state.getData() instanceof ClimateSensorAvro sensor) {
                return Collections.singletonList(sensor.getHumidity());
            } else {
                throw new IllegalArgumentException("Переданная сущность не является ClimateSensorAvro " +
                        state.getClass());
            }
        }
    };

    public abstract List<Integer> cast(SensorStateAvro state);
}

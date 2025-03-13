package ru.gofc.smart_home.hub.model.enums;

import ru.yandex.practicum.kafka.telemetry.event.*;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

public enum ConditionType {
    MOTION {
        @Override
        public Optional<List<Integer>> cast(SensorStateAvro state) {
            if (state == null) return Optional.empty();
            if (state.getData() instanceof MotionSensorAvro sensor) {
                return Optional.of(Collections.singletonList(sensor.getMotion() ? 1 : 0));
            } else {
                throw new IllegalArgumentException("Переданная сущность не является MotionSensorAvro");
            }
        }
    },
    LUMINOSITY {
        @Override
        public Optional<List<Integer>> cast(SensorStateAvro state) {
            if (state == null) return Optional.empty();
            if (state.getData() instanceof LightSensorAvro sensor) {
                return Optional.of(Collections.singletonList(sensor.getLuminosity()));
            } else {
                throw new IllegalArgumentException("Переданная сущность не является LightSensorAvro");
            }
        }
    },
    SWITCH {
        @Override
        public Optional<List<Integer>> cast(SensorStateAvro state) {
            if (state == null) return Optional.empty();
            if (state.getData() instanceof SwitchSensorAvro sensor) {
                return Optional.of(Collections.singletonList(sensor.getState() ? 1 : 0));
            } else {
                throw new IllegalArgumentException("Переданная сущность не является SwitchSensorAvro");
            }
        }
    },
    TEMPERATURE {
        @Override
        public Optional<List<Integer>> cast(SensorStateAvro state) {
            if (state == null) return Optional.empty();
            if (state.getData() instanceof TemperatureSensorAvro sensor) {
                return Optional.of(List.of(sensor.getTemperatureC(), sensor.getTemperatureF()));
            } if (state.getData() instanceof ClimateSensorAvro sensor) {
                return Optional.of(Collections.singletonList(sensor.getTemperatureC()));
            } else {
                throw new IllegalArgumentException("Переданная сущность не является TemperatureSensorAvro " +
                        "или ClimateSensorAvro");
            }
        }
    },
    CO2LEVEL {
        @Override
        public Optional<List<Integer>> cast(SensorStateAvro state) {
            if (state == null) return Optional.empty();
            if (state.getData() instanceof ClimateSensorAvro sensor) {
                return Optional.of(Collections.singletonList(sensor.getCo2Level()));
            } else {
                throw new IllegalArgumentException("Переданная сущность не является ClimateSensorAvro");
            }
        }
    },
    HUMIDITY {
        @Override
        public Optional<List<Integer>> cast(SensorStateAvro state) {
            if (state == null) return Optional.empty();
            if (state.getData() instanceof ClimateSensorAvro sensor) {
                return Optional.of(Collections.singletonList(sensor.getHumidity()));
            } else {
                throw new IllegalArgumentException("Переданная сущность не является ClimateSensorAvro");
            }
        }
    };

    public abstract Optional<List<Integer>> cast(SensorStateAvro state);
}

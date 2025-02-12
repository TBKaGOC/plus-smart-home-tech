package ru.gofc.smart_home.sensor.model;

import ru.gofc.smart_home.sensor.mapper.SensorEventMapper;

public enum SensorEventType {
    CLIMATE_SENSOR_EVENT {
        @Override
        public Object switchSensorMapper(SensorEvent event) {
            return SensorEventMapper.mapToClimateAvro((ClimateSensorEvent) event);
        }
    },
    LIGHT_SENSOR_EVENT {
        @Override
        public Object switchSensorMapper(SensorEvent event) {
            return SensorEventMapper.mapToLightAvro((LightSensorEvent) event);
        }
    },
    MOTION_SENSOR_EVENT {
        @Override
        public Object switchSensorMapper(SensorEvent event) {
            return SensorEventMapper.mapToMotionAvro((MotionSensorEvent) event);
        }
    },
    SWITCH_SENSOR_EVENT {
        @Override
        public Object switchSensorMapper(SensorEvent event) {
            return SensorEventMapper.mapToSwitchAvro((SwitchSensorEvent) event);
        }
    },
    TEMPERATURE_SENSOR_EVENT {
        @Override
        public Object switchSensorMapper(SensorEvent event) {
            return SensorEventMapper.mapToTemperatureAvro((TemperatureSensorEvent) event);
        }
    };

    public abstract Object switchSensorMapper(SensorEvent event);
}

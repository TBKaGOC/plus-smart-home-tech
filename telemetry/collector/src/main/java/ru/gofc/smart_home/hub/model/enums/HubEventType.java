package ru.gofc.smart_home.hub.model.enums;

import ru.gofc.smart_home.hub.mapper.HubEventMapper;
import ru.gofc.smart_home.hub.model.*;

public enum HubEventType {
    DEVICE_ADDED {
        @Override
        public Object switchHubEventMapper(HubEvent event) {
            return HubEventMapper.mapToDeviceAddedAvro((DeviceAddedEvent) event);
        }
    },
    DEVICE_REMOVED {
        @Override
        public Object switchHubEventMapper(HubEvent event) {
            return HubEventMapper.mapToDeviceRemovedAvro((DeviceRemovedEvent) event);
        }
    },
    SCENARIO_ADDED {
        @Override
        public Object switchHubEventMapper(HubEvent event) {
            return HubEventMapper.mapToScenarioAddedAvro((ScenarioAddedEvent) event);
        }
    },
    SCENARIO_REMOVED {
        @Override
        public Object switchHubEventMapper(HubEvent event) {
            return HubEventMapper.mapToScenarioRemovedAvro((ScenarioRemovedEvent) event);
        }
    };

    public abstract Object switchHubEventMapper(HubEvent event);
}

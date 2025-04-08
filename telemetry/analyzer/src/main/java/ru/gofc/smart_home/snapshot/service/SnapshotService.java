package ru.gofc.smart_home.snapshot.service;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import net.devh.boot.grpc.client.inject.GrpcClient;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.grpc.telemetry.event.DeviceActionRequest;
import ru.yandex.practicum.grpc.telemetry.hubrouter.HubRouterControllerGrpc.HubRouterControllerBlockingStub;

@FieldDefaults(level = AccessLevel.PRIVATE)
@Service
@Slf4j
public class SnapshotService {
    final HubRouterControllerBlockingStub hubRouterClient;

    public SnapshotService(@GrpcClient("hub-router")
                           HubRouterControllerBlockingStub hubRouterClient) {
        this.hubRouterClient = hubRouterClient;
    }

    public void sendMessage(DeviceActionRequest request) {
        log.info("Отправляю данные: {}", request.getAllFields());
        hubRouterClient.handleDeviceAction(request);
        log.info("Данные успешно отправлены");
    }
}

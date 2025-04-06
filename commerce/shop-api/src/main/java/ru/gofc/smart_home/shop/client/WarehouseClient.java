package ru.gofc.smart_home.shop.client;

import org.springframework.cloud.openfeign.FeignClient;
import ru.gofc.smart_home.shop.api.WarehouseInterface;

@FeignClient(name = "warehouse")
public interface WarehouseClient extends WarehouseInterface {
}

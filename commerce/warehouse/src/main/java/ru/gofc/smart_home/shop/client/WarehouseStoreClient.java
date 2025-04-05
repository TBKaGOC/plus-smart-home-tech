package ru.gofc.smart_home.shop.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import ru.gofc.smart_home.shop.request.SetProductQuantityStateRequest;

@FeignClient(name = "shopping-store")
public interface WarehouseStoreClient {
    @PostMapping("/api/v1/shopping-store/quantityState")
    boolean setProductQuantity(@RequestBody SetProductQuantityStateRequest request);
}

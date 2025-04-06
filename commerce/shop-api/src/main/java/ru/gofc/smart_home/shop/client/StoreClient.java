package ru.gofc.smart_home.shop.client;

import org.springframework.cloud.openfeign.FeignClient;
import ru.gofc.smart_home.shop.api.StoreInterface;

@FeignClient(name = "shopping-store")
public interface StoreClient extends StoreInterface {
}

package ru.gofc.smart_home.shop.client;

import org.springframework.cloud.openfeign.FeignClient;
import ru.gofc.smart_home.shop.api.OrderInterface;

@FeignClient(name = "order")
public interface OrderClient extends OrderInterface {
}

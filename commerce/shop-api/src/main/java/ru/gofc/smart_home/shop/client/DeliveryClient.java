package ru.gofc.smart_home.shop.client;

import org.springframework.cloud.openfeign.FeignClient;
import ru.gofc.smart_home.shop.api.DeliveryInterface;

@FeignClient(name = "delivery")
public interface DeliveryClient extends DeliveryInterface {
}

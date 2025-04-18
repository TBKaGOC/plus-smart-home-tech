package ru.gofc.smart_home.shop.client;

import org.springframework.cloud.openfeign.FeignClient;
import ru.gofc.smart_home.shop.api.PaymentInterface;

@FeignClient(name = "payment")
public interface PaymentClient extends PaymentInterface {
}

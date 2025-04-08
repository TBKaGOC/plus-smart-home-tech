package ru.gofc.smart_home.shop.client;

import org.springframework.cloud.openfeign.FeignClient;
import ru.gofc.smart_home.shop.api.CartInterface;

@FeignClient(name = "shopping-cart")
public interface CartClient extends CartInterface {
}

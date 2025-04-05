package ru.gofc.smart_home.shop.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import ru.gofc.smart_home.shop.dto.ProductDto;
import ru.gofc.smart_home.shop.exception.ProductNotFoundException;

@FeignClient(name = "shopping-store")
public interface StoreClient {
    @GetMapping("/api/v1/shopping-store/{productId}")
    ProductDto getProduct(@PathVariable("productId") String productId) throws ProductNotFoundException;
}

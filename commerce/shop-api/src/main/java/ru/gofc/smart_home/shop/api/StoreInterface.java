package ru.gofc.smart_home.shop.api;


import org.springframework.web.bind.annotation.*;
import ru.gofc.smart_home.shop.dto.ProductDto;
import ru.gofc.smart_home.shop.dto.enums.ProductCategory;
import ru.gofc.smart_home.shop.exception.ProductNotFoundException;
import ru.gofc.smart_home.shop.request.SetProductQuantityStateRequest;

import java.util.List;

@RequestMapping("/api/v1/shopping-store")
public interface StoreInterface {
    @GetMapping
    List<ProductDto> findProductByCategory(@RequestBody ProductCategory category);

    @PutMapping
    ProductDto saveProduct(@RequestBody ProductDto dto);

    @PostMapping
    ProductDto updateProduct(@RequestBody ProductDto dto) throws ProductNotFoundException;

    @PostMapping("/removeProductFromStore")
    boolean removeProduct(@RequestBody String id) throws ProductNotFoundException;

    @PostMapping("/quantityState")
    boolean setProductQuantity(@RequestBody SetProductQuantityStateRequest request) throws ProductNotFoundException;

    @GetMapping("/{productId}")
    ProductDto getProduct(@PathVariable String productId) throws ProductNotFoundException;
}

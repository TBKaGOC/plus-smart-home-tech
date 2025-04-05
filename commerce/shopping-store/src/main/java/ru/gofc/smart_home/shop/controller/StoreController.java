package ru.gofc.smart_home.shop.controller;

import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.*;
import ru.gofc.smart_home.shop.dto.ProductDto;
import ru.gofc.smart_home.shop.request.SetProductQuantityStateRequest;
import ru.gofc.smart_home.shop.dto.enums.ProductCategory;
import ru.gofc.smart_home.shop.exception.ProductNotFoundException;
import ru.gofc.smart_home.shop.service.ProductService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/shopping-store")
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class StoreController {
    final ProductService service;

    @GetMapping
    public List<ProductDto> findProductByCategory(@RequestBody ProductCategory category) {
        return service.findProductByCategory(category);
    }

    @PutMapping
    public ProductDto saveProduct(@RequestBody @Valid ProductDto dto) {
        return service.saveProduct(dto);
    }

    @PostMapping
    public ProductDto updateProduct(@RequestBody ProductDto dto) throws ProductNotFoundException {
        return service.updateProduct(dto);
    }

    @PostMapping("/removeProductFromStore")
    public boolean removeProduct(@RequestBody String id) throws ProductNotFoundException {
        return service.removeProduct(id);
    }

    @PostMapping("/quantityState")
    public boolean setProductQuantity(@RequestBody @Valid SetProductQuantityStateRequest request) throws ProductNotFoundException {
        return service.setProductQuantity(request);
    }

    @GetMapping("/{productId}")
    public ProductDto getProduct(@PathVariable String productId) throws ProductNotFoundException {
        return service.getProduct(productId);
    }
}

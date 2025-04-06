package ru.gofc.smart_home.shop.controller;

import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.*;
import ru.gofc.smart_home.shop.api.StoreInterface;
import ru.gofc.smart_home.shop.dto.ProductDto;
import ru.gofc.smart_home.shop.request.SetProductQuantityStateRequest;
import ru.gofc.smart_home.shop.dto.enums.ProductCategory;
import ru.gofc.smart_home.shop.exception.ProductNotFoundException;
import ru.gofc.smart_home.shop.service.ProductService;

import java.util.List;

@RestController
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class StoreController implements StoreInterface {
    final ProductService service;

    public List<ProductDto> findProductByCategory(ProductCategory category) {
        return service.findProductByCategory(category);
    }

    public ProductDto saveProduct(@Valid ProductDto dto) {
        return service.saveProduct(dto);
    }

    public ProductDto updateProduct(ProductDto dto) throws ProductNotFoundException {
        return service.updateProduct(dto);
    }

    public boolean removeProduct(String id) throws ProductNotFoundException {
        return service.removeProduct(id);
    }

    public boolean setProductQuantity(@Valid SetProductQuantityStateRequest request) throws ProductNotFoundException {
        return service.setProductQuantity(request);
    }

    public ProductDto getProduct(String productId) throws ProductNotFoundException {
        return service.getProduct(productId);
    }
}

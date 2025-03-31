package ru.gofc.smart_home.shop.mapper;

import ru.gofc.smart_home.shop.dto.ProductDto;
import ru.gofc.smart_home.shop.model.Product;

public class ProductMapper {
    public static Product mapToProduct(ProductDto dto) {
        return new Product(
                dto.getProductId(),
                dto.getProductName(),
                dto.getDescription(),
                dto.getImageSrc(),
                dto.getPrice(),
                dto.getProductState(),
                dto.getQuantityState(),
                dto.getProductCategory()
                );
    }

    public static ProductDto mapToProductDto(Product product) {
        return new ProductDto(
                product.getProductId(),
                product.getProductName(),
                product.getDescription(),
                product.getImageSrc(),
                product.getPrice(),
                product.getProductState(),
                product.getQuantityState(),
                product.getProductCategory()
        );
    }
}

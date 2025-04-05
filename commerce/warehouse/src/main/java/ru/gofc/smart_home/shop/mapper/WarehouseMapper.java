package ru.gofc.smart_home.shop.mapper;

import ru.gofc.smart_home.shop.model.WarehouseProduct;
import ru.gofc.smart_home.shop.request.NewProductInWarehouseRequest;

public class WarehouseMapper {
    public static WarehouseProduct mapRequest(NewProductInWarehouseRequest request) {
        return WarehouseProduct.builder()
                .id(request.getProductId())
                .fragile(request.getFragile())
                .weight(request.getWeight())
                .height(request.getDimension().getHeight())
                .width(request.getDimension().getWidth())
                .depth(request.getDimension().getDepth())
                .quantity(0)
                .build();
    }
}

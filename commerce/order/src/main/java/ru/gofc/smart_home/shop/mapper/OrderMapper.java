package ru.gofc.smart_home.shop.mapper;

import ru.gofc.smart_home.shop.dto.OrderDto;
import ru.gofc.smart_home.shop.model.Order;
import ru.gofc.smart_home.shop.model.OrderProductQuantity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class OrderMapper {
    public static Order mapToOrder(OrderDto dto, String username) {
        return new Order(
                dto.getOrderId(),
                dto.getShoppingCartId(),
                dto.getPaymentId(),
                dto.getDeliveryId(),
                dto.getState(),
                dto.getDeliveryWeight(),
                dto.getDeliveryVolume(),
                dto.getFragile(),
                dto.getDeliveryPrice(),
                dto.getProductPrice(),
                dto.getTotalPrice(),
                mapToQuantity(dto.getProducts()),
                username
        );
    }

    public static OrderDto mapToDto(Order order) {
        return new OrderDto(
                order.getId(),
                order.getShoppingCartId(),
                mapToMap(order.getProducts()),
                order.getPaymentId(),
                order.getDeliveryId(),
                order.getState(),
                order.getDeliveryWeight(),
                order.getDeliveryVolume(),
                order.getFragile(),
                order.getTotalPrice(),
                order.getDeliveryPrice(),
                order.getProductPrice()
        );
    }

    public static List<OrderProductQuantity> mapToQuantity(Map<String, Integer> products) {
        List<OrderProductQuantity> result = new ArrayList<>();

        for (String key: products.keySet()) {
            result.add(new OrderProductQuantity(key, products.get(key)));
        }
        return result;
    }

    public static Map<String, Integer> mapToMap(List<OrderProductQuantity> products) {
        Map<String, Integer> result = new HashMap<>();

        for (OrderProductQuantity product: products) {
            result.put(product.getProductId(), product.getQuantity());
        }
        return result;
    }
}

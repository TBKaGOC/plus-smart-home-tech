package ru.gofc.smart_home.shop.mapper;

import ru.gofc.smart_home.shop.dto.ShoppingCartDto;
import ru.gofc.smart_home.shop.model.Cart;
import ru.gofc.smart_home.shop.model.ProductQuantity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CartMapper {
    public static Cart mapToCart(ShoppingCartDto dto, String owner, Boolean isActivate) {
        return new Cart(dto.getShoppingCartId(), owner, mapToQuantity(dto.getProducts()), isActivate);
    }

    public static ShoppingCartDto mapToDto(Cart cart) {
        return new ShoppingCartDto(cart.getCartId(), mapToMap(cart.getProducts()));
    }

    public static List<ProductQuantity> mapToQuantity(Map<String, Integer> products) {
        List<ProductQuantity> result = new ArrayList<>();

        for (String key: products.keySet()) {
            result.add(new ProductQuantity(key, products.get(key)));
        }
        return result;
    }

    private static Map<String, Integer> mapToMap(List<ProductQuantity> products) {
        Map<String, Integer> result = new HashMap<>();

        for (ProductQuantity product: products) {
            result.put(product.getProductId(), product.getQuantity());
        }
        return result;
    }
}

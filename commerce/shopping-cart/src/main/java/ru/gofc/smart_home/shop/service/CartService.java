package ru.gofc.smart_home.shop.service;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.gofc.smart_home.shop.client.StoreClient;
import ru.gofc.smart_home.shop.client.WarehouseClient;
import ru.gofc.smart_home.shop.exception.*;
import ru.gofc.smart_home.shop.request.ChangeProductQuantityRequest;
import ru.gofc.smart_home.shop.dto.ProductDto;
import ru.gofc.smart_home.shop.dto.ShoppingCartDto;
import ru.gofc.smart_home.shop.mapper.CartMapper;
import ru.gofc.smart_home.shop.model.Cart;
import ru.gofc.smart_home.shop.model.ProductQuantity;
import ru.gofc.smart_home.shop.repository.CartRepository;

import java.util.*;

@Service
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Slf4j
public class CartService {
    final CartRepository repository;
    final StoreClient client;
    final WarehouseClient warehouseClient;

    public ShoppingCartDto findCartByOwner(String owner) throws NotAuthorizedUserException {
        validOwner(owner);
        log.info("Получение корзины пользователя " + owner);

        return CartMapper.mapToDto(getOwnerCart(owner));
    }

    @Transactional
    public ShoppingCartDto saveProduct(String owner, Map<String, Integer> product) throws NotAuthorizedUserException, CartNotActiveException, ProductInShoppingCartLowQuantityInWarehouse {
        validOwner(owner);
        log.info("Добавление продукта в корзину пользователя " + owner);

        Cart cart = getOwnerCart(owner);

        if (cart.getIsActivate()) {
            repository.deleteById(cart.getCartId());
            cart.getProducts().addAll(CartMapper.mapToQuantity(product));
            repository.save(cart);
            ShoppingCartDto dto = CartMapper.mapToDto(cart);
            warehouseClient.checkCart(dto);

            return dto;
        } else {
            throw new CartNotActiveException("Корзина " + cart.getCartId() + " деактивирована");
        }
    }

    @Transactional
    public void deactivateCart(String owner) throws NotAuthorizedUserException {
        validOwner(owner);
        log.info("Деактивация корзины пользователя " + owner);

        Cart cart = getOwnerCart(owner);
        cart.setIsActivate(false);
        repository.deleteById(cart.getCartId());
        repository.save(cart);
    }

    @Transactional
    public ShoppingCartDto removeProducts(String owner, Map<String, Integer> productsMap) throws NotAuthorizedUserException, NoProductsInShoppingCartException {
        validOwner(owner);
        log.info("Удаление продуктов из корзины пользователя " + owner);

        List<ProductQuantity> products = CartMapper.mapToQuantity(productsMap);
        Cart cart = getOwnerCart(owner);

        if (!new HashSet<>(cart.getProducts()).containsAll(products)) {
            throw new NoProductsInShoppingCartException("В корзине " + cart.getCartId() + " не найдены продукты из " + products);
        }

        cart.setProducts(products);
        repository.deleteById(cart.getCartId());
        repository.save(cart);

        return CartMapper.mapToDto(cart);
    }

    @Transactional
    public ProductDto changeQuantity(String owner, ChangeProductQuantityRequest request) throws NotAuthorizedUserException, NoProductsInShoppingCartException, ProductNotFoundException, ProductInShoppingCartLowQuantityInWarehouse {
        validOwner(owner);
        log.info("Изменение количества продукта " + request.getProductId() + " в корзине пользователя " + owner);

        Cart cart = getOwnerCart(owner);

        if (!cart.getProducts()
                .stream()
                .map(ProductQuantity::getProductId)
                .toList()
                .contains(request.getProductId())
        ) {
            throw new NoProductsInShoppingCartException("В корзине " + cart.getCartId() + " не найдены продукт" + request.getProductId());
        }

        for (ProductQuantity product: cart.getProducts()) {
            if (product.getProductId().equals(request.getProductId())) {
                cart.getProducts().remove(product);
                product.setQuantity(request.getNewQuantity());
                cart.getProducts().add(product);
            }
        }

        repository.deleteById(cart.getCartId());
        repository.save(cart);
        warehouseClient.checkCart(CartMapper.mapToDto(cart));


        return client.getProduct(request.getProductId());
    }

    private static void validOwner(String owner) throws NotAuthorizedUserException {
        if (owner == null || owner.isBlank()) {
            throw new NotAuthorizedUserException("Пользователь не авторизован для получения корзины");
        }
    }

    private Cart getOwnerCart(String owner) {
        return repository.findByOwner(owner).orElse(
                new Cart(UUID.randomUUID().toString(), owner, new ArrayList<>(), true
                ));
    }
}

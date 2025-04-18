package ru.gofc.smart_home.shop.service;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.gofc.smart_home.shop.client.StoreClient;
import ru.gofc.smart_home.shop.dto.*;
import ru.gofc.smart_home.shop.dto.enums.QuantityState;
import ru.gofc.smart_home.shop.exception.*;
import ru.gofc.smart_home.shop.mapper.WarehouseMapper;
import ru.gofc.smart_home.shop.model.OrderBooking;
import ru.gofc.smart_home.shop.model.WarehouseProduct;
import ru.gofc.smart_home.shop.repository.OrderRepository;
import ru.gofc.smart_home.shop.repository.WarehouseRepository;
import ru.gofc.smart_home.shop.request.*;

import java.security.SecureRandom;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Slf4j
public class WarehouseService {
    final WarehouseRepository repository;
    final StoreClient store;
    final OrderRepository orderRepository;
    static final String[] ADDRESSES =
            new String[] {"ADDRESS_1", "ADDRESS_2"};
    static final String CURRENT_ADDRESS =
            ADDRESSES[Random.from(new SecureRandom()).nextInt(0, 1)];

    @Transactional
    public void saveProduct(NewProductInWarehouseRequest request) throws SpecifiedProductAlreadyInWarehouseException {
        if (repository.existsById(request.getProductId())) {
            throw new SpecifiedProductAlreadyInWarehouseException("Товар " + request.getProductId() + " уже зарегестрирован");
        }

        repository.save(WarehouseMapper.mapRequest(request));
    }

    public BookedProductsDto checkCart(ShoppingCartDto cart) throws ProductInShoppingCartLowQuantityInWarehouse {
        for (String id: cart.getProducts().keySet()) {
            if (cart.getProducts().get(id) > repository.findQuantityById(id)) {
                throw new ProductInShoppingCartLowQuantityInWarehouse("Товара " + id + " недостаточно на складе");
            }
        }

        return createBooked(repository.findAllById(cart.getProducts().keySet()), cart.getProducts());
    }

    @Transactional
    public void addQuantity(AddProductToWarehouseRequest request) throws NoSpecifiedProductInWarehouseException, ProductNotFoundException {
        String id = request.getProductId();
        WarehouseProduct product = repository.findById(id)
                .orElseThrow(() -> new NoSpecifiedProductInWarehouseException("Нет информации о товаре " +
                        id));

        QuantityState oldQuantity = QuantityState.mapFromInteger(product.getQuantity());
        product.setQuantity(product.getQuantity() + request.getQuantity());
        QuantityState newQuantity = QuantityState.mapFromInteger(product.getQuantity());

        repository.deleteById(id);
        repository.save(product);

        if (!oldQuantity.equals(newQuantity)) {
            store.setProductQuantity(new SetProductQuantityStateRequest(id, newQuantity));
        }
    }

    public AddressDto getAddress() {
        return new AddressDto(CURRENT_ADDRESS, CURRENT_ADDRESS, CURRENT_ADDRESS, CURRENT_ADDRESS, CURRENT_ADDRESS);
    }

    @Transactional
    public BookedProductsDto assembly(AssemblyProductsForOrderRequest request) throws ProductInShoppingCartLowQuantityInWarehouse, NoOrderFoundException {
        for (String id : request.getProducts().keySet()) {
            if (request.getProducts().get(id) > repository.findQuantityById(id)) {
                throw new ProductInShoppingCartLowQuantityInWarehouse("Товара " + id + " недостаточно на складе");
            }
        }
        List<WarehouseProduct> products = repository.findAllById(request.getProducts().keySet());
        changeQuantity(products, request.getProducts(), true);

        orderRepository.save(new OrderBooking(
                request.getOrderId(),
                products,
                null
        ));

        return createBooked(products, request.getProducts());
    }

    @Transactional
    public void acceptReturn(Map<String, Integer> products) {
        List<WarehouseProduct> entities = repository.findAllById(products.keySet());
        changeQuantity(entities, products, false);
    }

    @Transactional
    public void shippedToDelivery(ShippedToDeliveryRequest request) throws NoOrderFoundException {
        OrderBooking booking = orderRepository.findById(request.getOrderId()).orElseThrow(
                () -> new NoOrderFoundException("Не найдена бронь для заказа " + request.getOrderId())
        );

        booking.setDeliveryId(request.getDeliveryId());
        orderRepository.deleteById(request.getOrderId());
        orderRepository.save(booking);
    }

    private BookedProductsDto createBooked(List<WarehouseProduct> products, Map<String, Integer> quantity) {
        boolean fragile = false;
        double weight = 0;
        double volume = 0;

        for (WarehouseProduct product: products) {
            fragile = fragile || product.getFragile();
            weight += product.getWeight() * quantity.get(product.getId());
            volume += product.getWidth() * product.getHeight() * product.getDepth() * quantity.get(product.getId());
        }

        return new BookedProductsDto(weight, volume, fragile);
    }

    private void changeQuantity(List<WarehouseProduct> products, Map<String, Integer> quantity, boolean reduce) {
        for (WarehouseProduct product: products) {
            if (reduce) {
                product.setQuantity(product.getQuantity() - quantity.get(product.getId()));
            } else {
                product.setQuantity(product.getQuantity() + quantity.get(product.getId()));
            }
        }

        repository.deleteAllById(products.stream().map(WarehouseProduct::getId).collect(Collectors.toList()));
        repository.saveAll(products);
    }
}

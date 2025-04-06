package ru.gofc.smart_home.shop.service;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.gofc.smart_home.shop.client.StoreClient;
import ru.gofc.smart_home.shop.dto.AddressDto;
import ru.gofc.smart_home.shop.dto.BookedProductsDto;
import ru.gofc.smart_home.shop.dto.ShoppingCartDto;
import ru.gofc.smart_home.shop.dto.enums.QuantityState;
import ru.gofc.smart_home.shop.exception.NoSpecifiedProductInWarehouseException;
import ru.gofc.smart_home.shop.exception.ProductInShoppingCartLowQuantityInWarehouse;
import ru.gofc.smart_home.shop.exception.ProductNotFoundException;
import ru.gofc.smart_home.shop.exception.SpecifiedProductAlreadyInWarehouseException;
import ru.gofc.smart_home.shop.mapper.WarehouseMapper;
import ru.gofc.smart_home.shop.model.WarehouseProduct;
import ru.gofc.smart_home.shop.repository.WarehouseRepository;
import ru.gofc.smart_home.shop.request.AddProductToWarehouseRequest;
import ru.gofc.smart_home.shop.request.NewProductInWarehouseRequest;
import ru.gofc.smart_home.shop.request.SetProductQuantityStateRequest;

import java.security.SecureRandom;
import java.util.List;
import java.util.Random;

@Service
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Slf4j
public class WarehouseService {
    final WarehouseRepository repository;
    final StoreClient client;
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

        return createBooked(repository.findAllById(cart.getProducts().keySet()));
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
            client.setProductQuantity(new SetProductQuantityStateRequest(id, newQuantity));
        }
    }

    public AddressDto getAddress() {
        return new AddressDto(CURRENT_ADDRESS, CURRENT_ADDRESS, CURRENT_ADDRESS, CURRENT_ADDRESS, CURRENT_ADDRESS);
    }

    private BookedProductsDto createBooked(List<WarehouseProduct> products) {
        boolean fragile = false;
        double weight = 0;
        double volume = 0;

        for (WarehouseProduct product: products) {
            fragile = fragile || product.getFragile();
            weight += product.getWeight();
            volume += product.getWidth() * product.getHeight() * product.getDepth();
        }

        return new BookedProductsDto(weight, volume, fragile);
    }
}

package ru.gofc.smart_home.shop.service;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.gofc.smart_home.shop.dto.ProductDto;
import ru.gofc.smart_home.shop.request.SetProductQuantityStateRequest;
import ru.gofc.smart_home.shop.dto.enums.ProductCategory;
import ru.gofc.smart_home.shop.dto.enums.ProductState;
import ru.gofc.smart_home.shop.exception.ProductNotFoundException;
import ru.gofc.smart_home.shop.mapper.ProductMapper;
import ru.gofc.smart_home.shop.model.Product;
import ru.gofc.smart_home.shop.repository.ProductRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Slf4j
public class ProductService {
    final ProductRepository repository;

    public List<ProductDto> findProductByCategory(ProductCategory category) {
        log.info("Поиск продуктов " + category);

        return repository.findByCategory(category).stream()
                .map(ProductMapper::mapToProductDto)
                .collect(Collectors.toList());
    }

    @Transactional
    public ProductDto saveProduct(ProductDto dto) {
        log.info("Сохранение продукта " + dto.getProductId());
        log.debug("Сохранение продукта " + dto);

        dto.setProductState(ProductState.ACTIVE);
        return ProductMapper.mapToProductDto(repository.save(ProductMapper.mapToProduct(dto)));
    }

    @Transactional
    public ProductDto updateProduct(ProductDto dto) throws ProductNotFoundException {
        log.info("Обновление продукта " + dto.getProductId());
        log.debug("Обновление продукта " + dto);

        Product old = repository.findById(dto.getProductId()).orElseThrow(
                () -> new ProductNotFoundException("Не найден продукт " + dto.getProductId())
        );

        Product newProduct = new Product();
        newProduct.setProductId(old.getProductId());
        newProduct.setProductName(dto.getProductName() == null ? old.getProductName() : dto.getProductName());
        newProduct.setDescription(dto.getDescription() == null ? old.getDescription() : dto.getDescription());
        newProduct.setImageSrc(dto.getImageSrc());
        newProduct.setPrice(dto.getPrice() == null ? old.getPrice() : dto.getPrice());
        newProduct.setQuantityState(dto.getQuantityState() == null ? old.getQuantityState() : dto.getQuantityState());
        newProduct.setProductCategory(dto.getProductCategory() == null ? old.getProductCategory() : dto.getProductCategory());

        repository.deleteById(newProduct.getProductId());
        return ProductMapper.mapToProductDto(repository.save(newProduct));
    }

    @Transactional
    public boolean removeProduct(String id) throws ProductNotFoundException {
        log.info("Удаление продукта " + id);

        Product product = repository.findById(id).orElseThrow(
                () -> new ProductNotFoundException("Не найден продукт " + id)
        );
        product.setProductState(ProductState.DEACTIVATE);
        repository.deleteById(id);
        repository.save(product);

        return true;
    }

    @Transactional
    public boolean setProductQuantity(SetProductQuantityStateRequest request) throws ProductNotFoundException {
        log.info("Обновление количества продукта " + request.getProductId());

        Product product = repository.findById(request.getProductId()).orElseThrow(
                () -> new ProductNotFoundException("Не найден продукт " + request.getProductId())
        );
        product.setQuantityState(request.getQuantityState());
        repository.deleteById(request.getProductId());
        repository.save(product);

        return true;
    }

    public ProductDto getProduct(String productId) throws ProductNotFoundException {
        log.info("Получение продукта " + productId);

        return ProductMapper.mapToProductDto(repository.findById(productId).orElseThrow(
                () -> new ProductNotFoundException("Не найден продукт " + productId)
        ));
    }
}

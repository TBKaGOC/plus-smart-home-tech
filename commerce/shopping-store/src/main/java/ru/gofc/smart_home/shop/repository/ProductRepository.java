package ru.gofc.smart_home.shop.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.gofc.smart_home.shop.dto.enums.ProductCategory;
import ru.gofc.smart_home.shop.model.Product;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, String> {
    List<Product> findByCategory(ProductCategory category);
}

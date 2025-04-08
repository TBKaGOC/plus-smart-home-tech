package ru.gofc.smart_home.shop.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;
import lombok.experimental.FieldDefaults;
import ru.gofc.smart_home.shop.dto.enums.ProductCategory;
import ru.gofc.smart_home.shop.dto.enums.ProductState;
import ru.gofc.smart_home.shop.dto.enums.QuantityState;

@Entity
@Table(name = "store_product")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Product {
    @Id
    @Column(name = "id")
    String productId;
    @Column(name = "name", nullable = false)
    String productName;
    @Column(nullable = false)
    String description;
    @Column(name = "image_src")
    String imageSrc;
    @Column(nullable = false)
    Float price;
    @Column(name = "state", nullable = false)
    ProductState productState;
    @Column(name = "quantity", nullable = false)
    QuantityState quantityState;
    @Column(name = "category", nullable = false)
    ProductCategory productCategory;
}

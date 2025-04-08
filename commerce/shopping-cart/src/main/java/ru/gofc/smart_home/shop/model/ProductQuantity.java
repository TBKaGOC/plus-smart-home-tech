package ru.gofc.smart_home.shop.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Entity
@Table(name = "product_quantity")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@ToString
public class ProductQuantity {
    @Id
    @Column(name = "id", nullable = false)
    String productId;
    @Column(nullable = false)
    Integer quantity;
}

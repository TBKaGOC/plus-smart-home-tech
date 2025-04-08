package ru.gofc.smart_home.shop.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Entity
@Table(name = "shopping-cart")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class WarehouseProduct {
    @Id
    @Column(nullable = false)
    String id;
    Boolean fragile;
    @Column(nullable = false)
    Double width;
    @Column(nullable = false)
    Double height;
    @Column(nullable = false)
    Double depth;
    @Column(nullable = false)
    Double weight;
    @Column(nullable = false)
    Integer quantity;
}

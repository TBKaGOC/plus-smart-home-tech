package ru.gofc.smart_home.shop.model;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Entity
@Table(name = "order_booking")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class OrderBooking {
    @Id String id;
    @ManyToMany
    @JoinTable(
            name = "booking_products",
            joinColumns = @JoinColumn(name = "booking_id"),
            inverseJoinColumns = @JoinColumn(name = "product_id")
    )
    List<WarehouseProduct> products;
    @Column(name = "delivery_id")
    String deliveryId;
}

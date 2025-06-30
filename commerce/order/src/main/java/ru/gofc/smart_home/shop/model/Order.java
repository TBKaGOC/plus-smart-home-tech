package ru.gofc.smart_home.shop.model;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import ru.gofc.smart_home.shop.dto.enums.OrderState;

import java.util.List;

@Entity
@Table(name = "shop_order")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@ToString
@Builder
public class Order {
    @Id
    String id;
    @Column(name = "shopping_cart_id")
    String shoppingCartId;
    @Column(name = "payment_id")
    String paymentId;
    @Column(name = "delivery_id")
    String deliveryId;
    OrderState state;
    @Column(name = "delivery_weight")
    Double deliveryWeight;
    @Column(name = "delivery_volume")
    Double deliveryVolume;
    Boolean fragile;
    @Column(name = "delivery_price")
    Double deliveryPrice;
    @Column(name = "product_price")
    Double productPrice;
    @Column(name = "total_price")
    Double totalPrice;
    @OneToMany(cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
    @JoinTable(
            name = "order_products",
            joinColumns = @JoinColumn(name = "order_id"),
            inverseJoinColumns = @JoinColumn(name = "product_id")
    )
    List<OrderProductQuantity> products;
    String username;
}

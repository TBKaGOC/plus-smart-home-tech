package ru.gofc.smart_home.shop.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Entity
@Table(name = "payment")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@ToString
public class Payment {
    @Id String id;
    @Column(name = "order_id") String orderId;
    @Column(name = "product_cost") Double productCost;
    @Column(name = "delivery_cost") Double deliveryCost;
    @Column(name = "total_cost") Double totalCost;
    PaymentStatus status;
}

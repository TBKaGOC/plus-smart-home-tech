package ru.gofc.smart_home.shop.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.gofc.smart_home.shop.model.OrderBooking;

public interface OrderRepository extends JpaRepository<OrderBooking, String> {
}

package ru.gofc.smart_home.shop.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.gofc.smart_home.shop.model.Order;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, String> {
    List<Order> findByUsername(String username);
}

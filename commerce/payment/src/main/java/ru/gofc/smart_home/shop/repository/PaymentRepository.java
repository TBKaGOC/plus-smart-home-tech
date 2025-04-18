package ru.gofc.smart_home.shop.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.gofc.smart_home.shop.model.Payment;

import java.util.Optional;

public interface PaymentRepository extends JpaRepository<Payment, String> {
    Optional<Payment> findByOrderId(String orderId);
}

package ru.gofc.smart_home.shop.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.gofc.smart_home.shop.model.Delivery;

import java.util.Optional;

public interface DeliveryRepository extends JpaRepository<Delivery, String> {
}

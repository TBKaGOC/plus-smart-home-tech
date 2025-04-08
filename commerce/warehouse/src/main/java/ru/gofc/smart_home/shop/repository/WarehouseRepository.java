package ru.gofc.smart_home.shop.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.gofc.smart_home.shop.model.WarehouseProduct;

public interface WarehouseRepository extends JpaRepository<WarehouseProduct, String> {
    @Query("SELECT p.quantity FROM WarehouseProduct p WHERE p.id = %:id%")
    Integer findQuantityById(@Param("id") String id);
}

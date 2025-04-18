package ru.gofc.smart_home.shop.mapper;

import ru.gofc.smart_home.shop.dto.PaymentDto;
import ru.gofc.smart_home.shop.model.Payment;

public class PaymentMapper {
    public static PaymentDto mapToDto(Payment payment) {
        return new PaymentDto(
                payment.getId(),
                payment.getTotalCost(),
                payment.getDeliveryCost(),
                payment.getTotalCost() - payment.getDeliveryCost() - payment.getProductCost()
        );
    }
}

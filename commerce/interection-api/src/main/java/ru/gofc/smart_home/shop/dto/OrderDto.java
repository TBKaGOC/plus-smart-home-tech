package ru.gofc.smart_home.shop.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import lombok.experimental.FieldDefaults;
import ru.gofc.smart_home.shop.dto.enums.OrderState;

import java.util.Map;

@FieldDefaults(level = AccessLevel.PRIVATE)
@AllArgsConstructor
@Getter
@Setter
@ToString
public class OrderDto {
    @NotBlank String orderId;
    String shoppingCartId;
    @NotNull Map<String, Integer> products;
    String paymentId;
    String deliveryId;
    OrderState state;
    Double deliveryWeight;
    Double deliveryVolume;
    Boolean fragile;
    Double totalPrice;
    Double deliveryPrice;
    Double productPrice;
}

package ru.gofc.smart_home.shop.request;

import jakarta.validation.constraints.NotBlank;
import lombok.*;
import lombok.experimental.FieldDefaults;

@FieldDefaults(level = AccessLevel.PRIVATE)
@AllArgsConstructor
@Getter
@Setter
@ToString
public class ShippedToDeliveryRequest {
    @NotBlank String orderId;
    @NotBlank String deliveryId;
}

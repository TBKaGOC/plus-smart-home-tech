package ru.gofc.smart_home.shop.dto;

import lombok.*;
import lombok.experimental.FieldDefaults;

@FieldDefaults(level = AccessLevel.PRIVATE)
@AllArgsConstructor
@Getter
@Setter
@ToString
public class PaymentDto {
    String paymentId;
    Double totalPayment;
    Double deliveryTotal;
    Double feeTotal;
}

package ru.gofc.smart_home.shop.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import lombok.experimental.FieldDefaults;
import ru.gofc.smart_home.shop.dto.enums.DeliveryState;

@FieldDefaults(level = AccessLevel.PRIVATE)
@AllArgsConstructor
@Getter
@Setter
@ToString
public class DeliveryDto {
    @NotBlank String deliveryId;
    @NotNull DeliveryState state;
    @NotNull AddressDto fromAddress;
    @NotNull AddressDto toAddress;
    @NotNull String orderId;
}

package ru.gofc.smart_home.shop.dto;

import jakarta.validation.constraints.NotNull;
import lombok.*;
import lombok.experimental.FieldDefaults;

@FieldDefaults(level = AccessLevel.PRIVATE)
@AllArgsConstructor
@Getter
@Setter
@ToString
public class BookedProductsDto {
    @NotNull Double deliveryWeight;
    @NotNull Double deliveryVolume;
    @NotNull Boolean fragile;
}

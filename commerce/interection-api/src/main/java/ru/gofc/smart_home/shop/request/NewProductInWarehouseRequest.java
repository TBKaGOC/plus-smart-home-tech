package ru.gofc.smart_home.shop.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import lombok.experimental.FieldDefaults;
import ru.gofc.smart_home.shop.dto.DimensionDto;

@FieldDefaults(level = AccessLevel.PRIVATE)
@AllArgsConstructor
@Getter
@Setter
@ToString
public class NewProductInWarehouseRequest {
    @NotBlank String productId;
    Boolean fragile;
    @NotNull DimensionDto dimension;
    @Min(1)
    @NotNull
    Double weight;
}

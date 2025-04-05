package ru.gofc.smart_home.shop.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import lombok.experimental.FieldDefaults;

@FieldDefaults(level = AccessLevel.PRIVATE)
@AllArgsConstructor
@Getter
@Setter
@ToString
public class AddProductToWarehouseRequest {
    String productId;
    @Min(1)
    @NotNull
    Integer quantity;
}

package ru.gofc.smart_home.shop.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import lombok.experimental.FieldDefaults;

@FieldDefaults(level = AccessLevel.PRIVATE)
@AllArgsConstructor
@Getter
@Setter
@ToString
public class ChangeProductQuantityRequest {
    @NotBlank String productId;
    @NotNull Integer newQuantity;
}

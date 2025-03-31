package ru.gofc.smart_home.shop.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import ru.gofc.smart_home.shop.dto.enums.QuantityState;

@FieldDefaults(level = AccessLevel.PRIVATE)
@RequiredArgsConstructor
@Getter
@Setter
public class SetProductQuantityStateRequest {
    @NotBlank
    String productId;
    @NotNull
    QuantityState quantityState;
}

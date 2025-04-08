package ru.gofc.smart_home.shop.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import lombok.experimental.FieldDefaults;
import ru.gofc.smart_home.shop.dto.enums.QuantityState;

@FieldDefaults(level = AccessLevel.PRIVATE)
@AllArgsConstructor
@Getter
@Setter
public class SetProductQuantityStateRequest {
    @NotBlank
    String productId;
    @NotNull
    QuantityState quantityState;
}

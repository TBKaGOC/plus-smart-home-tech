package ru.gofc.smart_home.shop.request;

import jakarta.validation.constraints.NotNull;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.Map;

@FieldDefaults(level = AccessLevel.PRIVATE)
@AllArgsConstructor
@Getter
@Setter
@ToString
public class AssemblyProductsForOrderRequest {
    @NotNull String orderId;
    @NotNull Map<String, Integer> products;
}

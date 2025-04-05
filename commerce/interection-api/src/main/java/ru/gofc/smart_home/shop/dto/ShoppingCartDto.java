package ru.gofc.smart_home.shop.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.Map;

@FieldDefaults(level = AccessLevel.PRIVATE)
@AllArgsConstructor
@Getter
@Setter
@ToString
public class ShoppingCartDto {
    @NotBlank String shoppingCartId;
    @NotNull Map<String, Integer> products;
}

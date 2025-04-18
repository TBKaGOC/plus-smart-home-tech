package ru.gofc.smart_home.shop.request;

import jakarta.validation.constraints.NotNull;
import lombok.*;
import lombok.experimental.FieldDefaults;
import ru.gofc.smart_home.shop.dto.AddressDto;
import ru.gofc.smart_home.shop.dto.ShoppingCartDto;

@FieldDefaults(level = AccessLevel.PRIVATE)
@AllArgsConstructor
@Getter
@Setter
@ToString
public class CreateNewOrderRequest {
    @NotNull AddressDto deliveryAddress;
    @NotNull ShoppingCartDto shoppingCart;
}

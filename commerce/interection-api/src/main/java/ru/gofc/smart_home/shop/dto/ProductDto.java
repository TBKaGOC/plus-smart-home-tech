package ru.gofc.smart_home.shop.dto;


import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import lombok.experimental.FieldDefaults;
import ru.gofc.smart_home.shop.dto.enums.ProductCategory;
import ru.gofc.smart_home.shop.dto.enums.ProductState;
import ru.gofc.smart_home.shop.dto.enums.QuantityState;

@FieldDefaults(level = AccessLevel.PRIVATE)
@AllArgsConstructor
@Getter
@Setter
@ToString
public class ProductDto {
    String productId;
    @NotBlank String productName;
    @NotBlank String description;
    String imageSrc;
    @Min(1) Float price;
    @NotNull ProductState productState;
    @NotNull QuantityState quantityState;
    ProductCategory productCategory;
}

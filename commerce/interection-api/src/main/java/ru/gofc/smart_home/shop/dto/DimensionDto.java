package ru.gofc.smart_home.shop.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import lombok.experimental.FieldDefaults;

@FieldDefaults(level = AccessLevel.PRIVATE)
@AllArgsConstructor
@Getter
@Setter
@ToString
public class DimensionDto {
    @Min(1)
    @NotNull
    Double width;
    @Min(1)
    @NotNull
    Double height;
    @Min(1)
    @NotNull
    Double depth;
}

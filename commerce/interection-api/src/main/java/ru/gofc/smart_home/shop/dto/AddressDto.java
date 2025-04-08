package ru.gofc.smart_home.shop.dto;

import lombok.*;
import lombok.experimental.FieldDefaults;

@FieldDefaults(level = AccessLevel.PRIVATE)
@AllArgsConstructor
@Getter
@Setter
@ToString
public class AddressDto {
    String country;
    String city;
    String street;
    String house;
    String flat;
}

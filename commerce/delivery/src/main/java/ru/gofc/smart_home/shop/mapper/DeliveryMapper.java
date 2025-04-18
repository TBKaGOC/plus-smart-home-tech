package ru.gofc.smart_home.shop.mapper;

import ru.gofc.smart_home.shop.dto.AddressDto;
import ru.gofc.smart_home.shop.dto.DeliveryDto;
import ru.gofc.smart_home.shop.model.Address;
import ru.gofc.smart_home.shop.model.Delivery;

public class DeliveryMapper {
    public static DeliveryDto mapToDto(Delivery delivery) {
        return new DeliveryDto(
                delivery.getId(),
                delivery.getState(),
                mapToAddressDto(delivery.getFrom()),
                mapToAddressDto(delivery.getTo()),
                delivery.getOrderId()
        );
    }

    public static Delivery mapToEntity(DeliveryDto dto) {
        return Delivery.builder()
                .id(dto.getDeliveryId())
                .state(dto.getState())
                .from(mapToAddress(dto.getFromAddress()))
                .to(mapToAddress(dto.getToAddress()))
                .orderId(dto.getOrderId())
                .build();
    }

    private static AddressDto mapToAddressDto(Address address) {
        return new AddressDto(
                address.getCountry(),
                address.getCity(),
                address.getStreet(),
                address.getHouse(),
                address.getFlat()
        );
    }

    private static Address mapToAddress(AddressDto address) {
        return new Address(
                null,
                address.getCountry(),
                address.getCity(),
                address.getStreet(),
                address.getHouse(),
                address.getFlat()
        );
    }
}

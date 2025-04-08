package ru.gofc.smart_home.shop.dto.enums;

public enum QuantityState {
    ENDED,
    FEW,
    ENOUGH,
    MANY;

    public static QuantityState mapFromInteger(Integer quantity) {
        if (quantity == 0) return ENDED;
        if (quantity < 10) return FEW;
        if (quantity <= 100) return ENOUGH;
        return MANY;
    }
}

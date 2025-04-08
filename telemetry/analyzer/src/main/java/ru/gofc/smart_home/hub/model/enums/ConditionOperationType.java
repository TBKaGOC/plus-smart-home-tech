package ru.gofc.smart_home.hub.model.enums;

import java.util.Objects;

public enum ConditionOperationType {
    EQUALS {
        @Override
        public boolean handle(Integer constant, Integer variable) {
            return Objects.equals(constant, variable);
        }
    },
    GREATER_THAN {
        @Override
        public boolean handle(Integer constant, Integer variable) {
            return constant < variable;
        }
    },
    LOWER_THAN {
        @Override
        public boolean handle(Integer constant, Integer variable) {
            return constant > variable;
        }
    };

    public abstract boolean handle(Integer constant, Integer variable);
}

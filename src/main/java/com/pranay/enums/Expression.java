package com.pranay.enums;

public enum Expression {
    ANY("*"), LIST(","),  INTERVAL("/"), RANGE ("-");

    private final String value;
    Expression(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    @Override
    public String toString() {
        return this.getValue();
    }
}

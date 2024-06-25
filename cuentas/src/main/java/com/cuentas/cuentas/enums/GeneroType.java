package com.cuentas.cuentas.enums;

public enum GeneroType {
    MASCULINO("Masculino"), FEMENINO("Femenino"),NO_IDENTIFICA("No Identifica");

    private final String value;

    GeneroType(String value)
    {
        this.value = value;
    }

    public String getValue()
    {
        return value;
    }
}
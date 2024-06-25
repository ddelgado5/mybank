package com.personas.personas.enums;

public enum GeneroType
{
    MASCULINO("MASCULINO"), FEMENINO("FEMENINO"),NO_IDENTIFICA("NO IDENTIFICA");

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
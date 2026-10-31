package com.ingescompany.doofplus.platform.organizations.domain.model.valueobjects;

public record Ruc(String value) {
    public Ruc {
        if (value == null || !value.matches("\\d{11}")) {
            throw new IllegalArgumentException("El RUC debe tener exactamente 11 dígitos numéricos.");
        }
    }
}
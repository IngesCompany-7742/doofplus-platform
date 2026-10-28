package com.ingescompany.doofplus.platform.monitoring.domain.model.valueobjects;

public record Facility(String location) {
    private static final int MAX_LENGTH = 256;
    public Facility {
        if (location == null || location.isBlank()) {
            throw new IllegalArgumentException("monitoring.equipment.error.location.notBlank");
        }
        if (location.length() > MAX_LENGTH) {
            throw new IllegalArgumentException("monitoring.equipment.error.location.size");
        }
    }
    public String value() {
        return location;
    }
}
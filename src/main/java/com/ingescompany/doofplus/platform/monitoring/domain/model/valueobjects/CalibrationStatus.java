package com.ingescompany.doofplus.platform.monitoring.domain.model.valueobjects;

public enum CalibrationStatus {
    FIT("fit"),
    DUE("due"),
    NOT_FIT("not-fit");
    private final String value;

    CalibrationStatus(String value) {
        this.value = value;
    }

    public String value() {
        return value;
    }

    public static CalibrationStatus from(String value) {
        if (value == null || value.isBlank()) { return FIT; }
        for (CalibrationStatus status : values()) {
            if (status.value.equalsIgnoreCase(value.trim()) ||
                    status.name().equalsIgnoreCase(value.trim()))
            {   return status;  }
        } return FIT;
    }
}
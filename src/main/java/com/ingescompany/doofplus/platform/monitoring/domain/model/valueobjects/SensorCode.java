package com.ingescompany.doofplus.platform.monitoring.domain.model.valueobjects;

import java.util.regex.Pattern;

/**
 * Sensor identifier code, such as T-204, H-118, P-031.
 *
 * @param value sensor code string
 */
public record SensorCode(String value) {
    private static final int MAX_LENGTH = 64;
    private static final Pattern ALLOWED_PATTERN = Pattern.compile("^[A-Za-z0-9._:-]+$");
    public SensorCode {
        if (value == null || value.isBlank()) {
            throw new IllegalArgumentException("monitoring.sensor.error.code.notBlank");
        }
        if (value.length() > MAX_LENGTH) {
            throw new IllegalArgumentException("monitoring.sensor.error.code.size");
        }
        if (!ALLOWED_PATTERN.matcher(value).matches()) {
            throw new IllegalArgumentException("monitoring.sensor.error.code.pattern");
        }
    }
}
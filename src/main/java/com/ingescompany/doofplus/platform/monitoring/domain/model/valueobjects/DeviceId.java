package com.ingescompany.doofplus.platform.monitoring.domain.model.valueobjects;

import java.util.regex.Pattern;

/**
 * ThingsBoard IoT device identifier, such as tb-7f3a2c91-coat02-temp.
 *
 * @param value device identifier string
 */
public record DeviceId(String value) {
    private static final int MAX_LENGTH = 128;
    private static final Pattern ALLOWED_PATTERN = Pattern.compile("^[A-Za-z0-9._:-]+$");
    public DeviceId {
        if (value == null || value.isBlank()) {
            throw new IllegalArgumentException("monitoring.sensor.error.deviceId.notBlank");
        }
        if (value.length() > MAX_LENGTH) {
            throw new IllegalArgumentException("monitoring.sensor.error.deviceId.size");
        }
        if (!ALLOWED_PATTERN.matcher(value).matches()) {
            throw new IllegalArgumentException("monitoring.sensor.error.deviceId.pattern");
        }
    }
}
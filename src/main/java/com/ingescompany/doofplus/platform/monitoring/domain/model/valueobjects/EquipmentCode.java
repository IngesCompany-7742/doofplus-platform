package com.ingescompany.doofplus.platform.monitoring.domain.model.valueobjects;

import java.util.regex.Pattern;

/**
 * Unique alphanumeric code identifying a piece of equipment, such as EQ-COAT-02.
 *
 * @param value equipment code string
 */
public record EquipmentCode(String value) {
    private static final int MAX_LENGTH = 64;
    private static final Pattern ALLOWED_PATTERN = Pattern.compile("^[A-Za-z0-9._:-]+$");
    public EquipmentCode {
        if (value == null || value.isBlank()) {
            throw new IllegalArgumentException("monitoring.equipment.error.code.notBlank");
        }
        if (value.length() > MAX_LENGTH) {
            throw new IllegalArgumentException("monitoring.equipment.error.code.size");
        }
        if (!ALLOWED_PATTERN.matcher(value).matches()) {
            throw new IllegalArgumentException("monitoring.equipment.error.code.pattern");
        }
    }
}
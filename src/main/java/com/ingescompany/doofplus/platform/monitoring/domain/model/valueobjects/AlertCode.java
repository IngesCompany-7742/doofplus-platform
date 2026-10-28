package com.ingescompany.doofplus.platform.monitoring.domain.model.valueobjects;

import java.util.regex.Pattern;

public record AlertCode(String value) {
    private static final int MAX_LENGTH = 64;
    private static final Pattern ALLOWED_PATTERN = Pattern.compile("^[A-Za-z0-9._:-]+$");
    public AlertCode {
        if (value == null || value.isBlank()) {
            throw new IllegalArgumentException("monitoring.alert.error.code.notBlank");
        }
        if (value.length() > MAX_LENGTH) {
            throw new IllegalArgumentException("monitoring.alert.error.code.size");
        }
        if (!ALLOWED_PATTERN.matcher(value).matches()) {
            throw new IllegalArgumentException("monitoring.alert.error.code.pattern");
        }
    }
}
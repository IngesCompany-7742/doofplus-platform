package com.ingescompany.doofplus.platform.iam.domain.model.valueobjects;

import java.util.regex.Pattern;

/**
 * Email address the user signs in with.
 *
 * @param value email address
 */
public record EmailAddress(String value) {
    private static final int MAX_LENGTH = 254;
    private static final Pattern ALLOWED_PATTERN = Pattern.compile("^[\\w.+-]+@[\\w-]+(\\.[\\w-]+)+$");
    private static final String NOT_BLANK_MESSAGE_KEY = "user.error.emailAddress.notBlank";
    private static final String SIZE_MESSAGE_KEY = "user.error.emailAddress.size";
    private static final String PATTERN_MESSAGE_KEY = "user.error.emailAddress.pattern";

    public EmailAddress {
        if (value == null || value.isBlank()) {
            throw new IllegalArgumentException(NOT_BLANK_MESSAGE_KEY);
        }
        if (value.length() > MAX_LENGTH) {
            throw new IllegalArgumentException(SIZE_MESSAGE_KEY);
        }
        if (!ALLOWED_PATTERN.matcher(value).matches()) {
            throw new IllegalArgumentException(PATTERN_MESSAGE_KEY);
        }
    }
}

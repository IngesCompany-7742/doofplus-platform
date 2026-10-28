package com.ingescompany.doofplus.platform.iam.domain.model.valueobjects;

/**
 * Identifier of the organization (tenant) the user belongs to (Organizations & Profiles bounded context).
 *
 * @param value organization identifier
 */
public record OrganizationId(Long value) {
    private static final String INVALID_MESSAGE_KEY = "user.error.organizationId.invalid";

    public OrganizationId {
        if (value == null || value <= 0) {
            throw new IllegalArgumentException(INVALID_MESSAGE_KEY);
        }
    }
}

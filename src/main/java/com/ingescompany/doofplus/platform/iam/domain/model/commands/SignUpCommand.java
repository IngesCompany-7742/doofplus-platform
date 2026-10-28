package com.ingescompany.doofplus.platform.iam.domain.model.commands;

import com.ingescompany.doofplus.platform.iam.domain.model.entities.Role;
import com.ingescompany.doofplus.platform.iam.domain.model.valueobjects.EmailAddress;
import com.ingescompany.doofplus.platform.iam.domain.model.valueobjects.OrganizationId;

import java.util.List;

/**
 * Command to register a user in an organization.
 *
 * @param emailAddress   email address used to sign in
 * @param password       password in plain text, hashed by the application layer
 * @param organizationId organization (tenant) the user belongs to
 * @param roles          roles assigned to the user; the default role when empty
 */
public record SignUpCommand(EmailAddress emailAddress, String password, OrganizationId organizationId,
                            List<Role> roles) {
    private static final int MIN_PASSWORD_LENGTH = 8;

    public SignUpCommand {
        if (emailAddress == null) {
            throw new IllegalArgumentException("user.error.emailAddress.notBlank");
        }
        if (password == null || password.isBlank()) {
            throw new IllegalArgumentException("user.error.password.notBlank");
        }
        if (password.length() < MIN_PASSWORD_LENGTH) {
            throw new IllegalArgumentException("user.error.password.size");
        }
        if (organizationId == null) {
            throw new IllegalArgumentException("user.error.organizationId.invalid");
        }
    }
}

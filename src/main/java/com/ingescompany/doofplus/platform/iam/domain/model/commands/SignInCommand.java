package com.ingescompany.doofplus.platform.iam.domain.model.commands;

import com.ingescompany.doofplus.platform.iam.domain.model.valueobjects.EmailAddress;

/**
 * Command to sign in with email address and password (US06).
 *
 * @param emailAddress email address of the user
 * @param password     password in plain text
 */
public record SignInCommand(EmailAddress emailAddress, String password) {
    public SignInCommand {
        if (emailAddress == null) {
            throw new IllegalArgumentException("user.error.emailAddress.notBlank");
        }
        if (password == null || password.isBlank()) {
            throw new IllegalArgumentException("user.error.password.notBlank");
        }
    }
}

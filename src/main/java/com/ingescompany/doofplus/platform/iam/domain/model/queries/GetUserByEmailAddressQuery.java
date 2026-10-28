package com.ingescompany.doofplus.platform.iam.domain.model.queries;

import com.ingescompany.doofplus.platform.iam.domain.model.valueobjects.EmailAddress;

/**
 * Query to get a user by its email address.
 *
 * @param emailAddress email address of the user
 */
public record GetUserByEmailAddressQuery(EmailAddress emailAddress) {
    public GetUserByEmailAddressQuery {
        if (emailAddress == null) {
            throw new IllegalArgumentException("user.error.emailAddress.notBlank");
        }
    }
}

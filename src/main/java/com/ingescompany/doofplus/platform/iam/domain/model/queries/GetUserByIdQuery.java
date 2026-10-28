package com.ingescompany.doofplus.platform.iam.domain.model.queries;

/**
 * Query to get a user by its identifier.
 *
 * @param userId user identifier
 */
public record GetUserByIdQuery(Long userId) {
    public GetUserByIdQuery {
        if (userId == null || userId <= 0) {
            throw new IllegalArgumentException("user.error.id.invalid");
        }
    }
}

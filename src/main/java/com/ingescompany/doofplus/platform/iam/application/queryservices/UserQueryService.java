package com.ingescompany.doofplus.platform.iam.application.queryservices;

import com.ingescompany.doofplus.platform.iam.domain.model.aggregates.User;
import com.ingescompany.doofplus.platform.iam.domain.model.queries.GetUserByEmailAddressQuery;
import com.ingescompany.doofplus.platform.iam.domain.model.queries.GetUserByIdQuery;

import java.util.Optional;

/**
 * Application service contract providing read access to users.
 *
 * @since 1.0
 */
public interface UserQueryService {
    /**
     * Retrieves a user by its identifier.
     *
     * @param query query containing the user identifier
     * @return user when found, otherwise empty
     */
    Optional<User> handle(GetUserByIdQuery query);

    /**
     * Retrieves a user by its email address.
     *
     * @param query query containing the email address
     * @return user when found, otherwise empty
     */
    Optional<User> handle(GetUserByEmailAddressQuery query);
}

package com.ingescompany.doofplus.platform.iam.infrastructure.persistence.jpa;

import com.ingescompany.doofplus.platform.iam.domain.model.aggregates.User;
import com.ingescompany.doofplus.platform.iam.domain.model.valueobjects.EmailAddress;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Repository for querying and persisting {@link User} aggregates.
 * {@code existsByEmailAddress()} checks the unique email address invariant.
 *
 * @since 1.0
 */
@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    /**
     * Find a user by email address.
     * @param emailAddress Email address
     * @return User when found, otherwise empty
     */
    Optional<User> findByEmailAddress(EmailAddress emailAddress);

    /**
     * Check if a user exists by email address.
     * @param emailAddress Email address
     * @return True if exists, false otherwise
     */
    boolean existsByEmailAddress(EmailAddress emailAddress);
}

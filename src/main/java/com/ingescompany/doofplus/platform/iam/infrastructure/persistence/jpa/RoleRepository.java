package com.ingescompany.doofplus.platform.iam.infrastructure.persistence.jpa;

import com.ingescompany.doofplus.platform.iam.domain.model.entities.Role;
import com.ingescompany.doofplus.platform.iam.domain.model.valueobjects.Roles;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Repository for querying and persisting {@link Role} entities.
 *
 * @since 1.0
 */
@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
    /**
     * Find a role by name.
     * @param name Role name
     * @return Role when found, otherwise empty
     */
    Optional<Role> findByName(Roles name);

    /**
     * Check if a role exists by name.
     * @param name Role name
     * @return True if exists, false otherwise
     */
    boolean existsByName(Roles name);
}

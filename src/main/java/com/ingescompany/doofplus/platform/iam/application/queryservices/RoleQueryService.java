package com.ingescompany.doofplus.platform.iam.application.queryservices;

import com.ingescompany.doofplus.platform.iam.domain.model.entities.Role;
import com.ingescompany.doofplus.platform.iam.domain.model.queries.GetAllRolesQuery;

import java.util.List;

/**
 * Application service contract providing read access to roles.
 *
 * @since 1.0
 */
public interface RoleQueryService {
    /**
     * Retrieves all roles.
     *
     * @param query query without parameters
     * @return list of roles, possibly empty
     */
    List<Role> handle(GetAllRolesQuery query);
}

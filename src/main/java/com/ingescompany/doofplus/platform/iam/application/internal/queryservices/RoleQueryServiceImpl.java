package com.ingescompany.doofplus.platform.iam.application.internal.queryservices;

import com.ingescompany.doofplus.platform.iam.application.queryservices.RoleQueryService;
import com.ingescompany.doofplus.platform.iam.domain.model.entities.Role;
import com.ingescompany.doofplus.platform.iam.domain.model.queries.GetAllRolesQuery;
import com.ingescompany.doofplus.platform.iam.infrastructure.persistence.jpa.RoleRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Application service for role query operations.
 *
 * @since 1.0
 */
@Slf4j
@Service
@Transactional(readOnly = true)
public class RoleQueryServiceImpl implements RoleQueryService {
    private final RoleRepository roleRepository;

    public RoleQueryServiceImpl(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Role> handle(GetAllRolesQuery query) {
        var results = roleRepository.findAll();
        log.debug("Found {} role(s)", results.size());
        return results;
    }
}

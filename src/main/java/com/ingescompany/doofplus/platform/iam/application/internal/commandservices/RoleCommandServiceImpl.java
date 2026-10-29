package com.ingescompany.doofplus.platform.iam.application.internal.commandservices;

import com.ingescompany.doofplus.platform.iam.application.commandservices.RoleCommandService;
import com.ingescompany.doofplus.platform.iam.domain.model.commands.SeedRolesCommand;
import com.ingescompany.doofplus.platform.iam.domain.model.entities.Role;
import com.ingescompany.doofplus.platform.iam.domain.model.valueobjects.Roles;
import com.ingescompany.doofplus.platform.iam.infrastructure.persistence.jpa.RoleRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;

/**
 * Application service for role command operations.
 * Creates each value of {@link Roles} once, so it can run on every startup.
 *
 * @since 1.0
 */
@Slf4j
@Service
public class RoleCommandServiceImpl implements RoleCommandService {
    private final RoleRepository roleRepository;

    public RoleCommandServiceImpl(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional
    public void handle(SeedRolesCommand command) {
        Arrays.stream(Roles.values()).forEach(role -> {
            if (!roleRepository.existsByName(role)) {
                roleRepository.save(new Role(role));
                log.info("Role created: name={}", role.name());
            }
        });
    }
}

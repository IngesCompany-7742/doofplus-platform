package com.ingescompany.doofplus.platform.iam.application.commandservices;

import com.ingescompany.doofplus.platform.iam.domain.model.commands.SeedRolesCommand;

/**
 * Application service contract for role command operations.
 *
 * @since 1.0
 */
public interface RoleCommandService {
    /**
     * Handles the creation of the roles that do not exist yet.
     *
     * @param command seed command without parameters
     * @see SeedRolesCommand
     */
    void handle(SeedRolesCommand command);
}

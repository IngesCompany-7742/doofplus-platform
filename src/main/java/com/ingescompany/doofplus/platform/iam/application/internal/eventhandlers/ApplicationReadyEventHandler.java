package com.ingescompany.doofplus.platform.iam.application.internal.eventhandlers;

import com.ingescompany.doofplus.platform.iam.application.commandservices.RoleCommandService;
import com.ingescompany.doofplus.platform.iam.domain.model.commands.SeedRolesCommand;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

/**
 * Seeds the roles when the application is ready to serve requests.
 *
 * @since 1.0
 */
@Slf4j
@Service("iamApplicationReadyEventHandler")
public class ApplicationReadyEventHandler {
    private final RoleCommandService roleCommandService;

    public ApplicationReadyEventHandler(RoleCommandService roleCommandService) {
        this.roleCommandService = roleCommandService;
    }

    /**
     * Handles the application ready event.
     *
     * @param event application ready event
     */
    @EventListener
    public void on(ApplicationReadyEvent event) {
        log.info("Verifying if roles seeding is needed for {}", event.getApplicationContext().getId());
        roleCommandService.handle(new SeedRolesCommand());
    }
}

package com.ingescompany.doofplus.platform.iam.application.commandservices;

import com.ingescompany.doofplus.platform.iam.domain.model.aggregates.User;
import com.ingescompany.doofplus.platform.iam.domain.model.commands.SignUpCommand;
import com.ingescompany.doofplus.platform.shared.application.result.Result;

/**
 * Application service contract for user command operations.
 *
 * @since 1.0
 */
public interface UserCommandService {
    /**
     * Handles the sign-up of a user.
     *
     * @param command sign-up command containing the user data
     * @return success when the user is created, failure when the email address is already registered
     *         or a role does not exist
     * @see SignUpCommand
     */
    Result<User, UserCommandFailure> handle(SignUpCommand command);
}

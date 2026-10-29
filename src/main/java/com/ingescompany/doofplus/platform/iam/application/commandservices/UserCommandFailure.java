package com.ingescompany.doofplus.platform.iam.application.commandservices;

/**
 * Failure types for user command execution.
 */
public sealed interface UserCommandFailure permits UserCommandFailure.EmailAddressAlreadyRegistered,
        UserCommandFailure.RoleNotFound {
    /**
     * @return message key associated with the failure
     */
    String messageKey();

    /**
     * Another user already signed up with the email address.
     */
    record EmailAddressAlreadyRegistered() implements UserCommandFailure {
        @Override
        public String messageKey() {
            return "user.error.emailAddress.duplicate";
        }
    }

    /**
     * One of the requested roles does not exist.
     */
    record RoleNotFound() implements UserCommandFailure {
        @Override
        public String messageKey() {
            return "user.error.roleNotFound";
        }
    }
}

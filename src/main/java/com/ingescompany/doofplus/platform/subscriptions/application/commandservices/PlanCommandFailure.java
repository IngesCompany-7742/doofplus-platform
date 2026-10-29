package com.ingescompany.doofplus.platform.subscriptions.application.commandservices;

/**
 * Failure types for plan command execution.
 */
public sealed interface PlanCommandFailure permits PlanCommandFailure.Duplicate {
    /**
     * @return message key associated with the failure
     */
    String messageKey();

    /**
     * Duplicate plan key failure.
     */
    record Duplicate() implements PlanCommandFailure {
        @Override
        public String messageKey() {
            return "plan.error.duplicate";
        }
    }
}

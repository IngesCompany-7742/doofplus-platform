package com.ingescompany.doofplus.platform.subscriptions.application.commandservices;

/**
 * Failure types for subscription command execution.
 */
public sealed interface SubscriptionCommandFailure permits SubscriptionCommandFailure.PlanNotFound,
        SubscriptionCommandFailure.AlreadySubscribed, SubscriptionCommandFailure.NotFound,
        SubscriptionCommandFailure.InvalidState {
    /**
     * @return message key associated with the failure
     */
    String messageKey();

    /**
     * The selected plan does not exist.
     */
    record PlanNotFound() implements SubscriptionCommandFailure {
        @Override
        public String messageKey() {
            return "subscription.error.planNotFound";
        }
    }

    /**
     * The organization already has a pending or active subscription.
     */
    record AlreadySubscribed() implements SubscriptionCommandFailure {
        @Override
        public String messageKey() {
            return "subscription.error.alreadySubscribed";
        }
    }

    /**
     * The subscription does not exist.
     */
    record NotFound() implements SubscriptionCommandFailure {
        @Override
        public String messageKey() {
            return "subscription.error.notFound";
        }
    }

    /**
     * The subscription state does not allow the operation.
     *
     * @param messageKey message key raised by the aggregate
     */
    record InvalidState(String messageKey) implements SubscriptionCommandFailure {
    }
}

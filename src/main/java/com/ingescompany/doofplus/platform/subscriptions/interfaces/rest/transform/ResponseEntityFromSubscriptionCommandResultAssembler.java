package com.ingescompany.doofplus.platform.subscriptions.interfaces.rest.transform;

import com.ingescompany.doofplus.platform.subscriptions.application.commandservices.SubscriptionCommandFailure;
import com.ingescompany.doofplus.platform.subscriptions.domain.model.aggregates.Subscription;
import com.ingescompany.doofplus.platform.shared.application.result.Result;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;

/**
 * Interface layer translator converting a subscription command result into an HTTP ResponseEntity.
 *
 * @since 1.0
 */
public class ResponseEntityFromSubscriptionCommandResultAssembler {
    /**
     * Converts a subscription command result to a ResponseEntity.
     *
     * @param result result of the subscription command execution
     * @param successStatus status returned on success (201 on creation, 200 on state changes)
     * @param messageSource source for localized response messages
     * @return ResponseEntity with either the subscription resource or a problem detail
     */
    public static ResponseEntity<?> toResponseEntityFromResult(
            Result<Subscription, SubscriptionCommandFailure> result,
            HttpStatus successStatus,
            MessageSource messageSource) {
        return result.fold(
                subscription -> new ResponseEntity<>(SubscriptionResourceFromEntityAssembler.toResourceFromEntity(subscription), successStatus),
                failure -> {
                    var status = statusFromFailure(failure);
                    var detail = messageSource.getMessage(failure.messageKey(), null, failure.messageKey(), LocaleContextHolder.getLocale());
                    return ResponseEntity.status(status).body(ProblemDetail.forStatusAndDetail(status, detail));
                });
    }

    private static HttpStatus statusFromFailure(SubscriptionCommandFailure failure) {
        return switch (failure) {
            case SubscriptionCommandFailure.PlanNotFound ignored -> HttpStatus.NOT_FOUND;
            case SubscriptionCommandFailure.NotFound ignored -> HttpStatus.NOT_FOUND;
            case SubscriptionCommandFailure.AlreadySubscribed ignored -> HttpStatus.CONFLICT;
            case SubscriptionCommandFailure.InvalidState ignored -> HttpStatus.CONFLICT;
        };
    }
}

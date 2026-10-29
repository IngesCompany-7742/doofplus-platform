package com.ingescompany.doofplus.platform.subscriptions.interfaces.rest.transform;

import com.ingescompany.doofplus.platform.subscriptions.application.commandservices.PlanCommandFailure;
import com.ingescompany.doofplus.platform.subscriptions.domain.model.aggregates.Plan;
import com.ingescompany.doofplus.platform.shared.application.result.Result;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;

import static org.springframework.http.HttpStatus.CREATED;

/**
 * Interface layer translator converting a plan command result into an HTTP ResponseEntity.
 *
 * @since 1.0
 */
public class ResponseEntityFromPlanCommandResultAssembler {
    /**
     * Converts a plan command result to a ResponseEntity.
     *
     * @param result result of the plan command execution
     * @param messageSource source for localized response messages
     * @return ResponseEntity with either the created plan resource or a conflict problem detail
     */
    public static ResponseEntity<?> toResponseEntityFromResult(Result<Plan, PlanCommandFailure> result, MessageSource messageSource) {
        return result.fold(
                plan -> new ResponseEntity<>(PlanResourceFromEntityAssembler.toResourceFromEntity(plan), CREATED),
                failure -> {
                    var status = HttpStatus.CONFLICT;
                    var detail = messageSource.getMessage(failure.messageKey(), null, failure.messageKey(), LocaleContextHolder.getLocale());
                    return ResponseEntity.status(status).body(ProblemDetail.forStatusAndDetail(status, detail));
                });
    }
}

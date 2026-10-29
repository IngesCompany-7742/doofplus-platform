package com.ingescompany.doofplus.platform.subscriptions.interfaces.rest.transform;

import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.function.Function;

/**
 * Interface layer translator converting plan and subscription query results into HTTP ResponseEntity values.
 *
 * @since 1.0
 */
public class ResponseEntityFromSubscriptionsQueryResultAssembler {
    /**
     * Converts a single aggregate into a 200 response.
     *
     * @param entity aggregate to map
     * @param toResource assembler from the aggregate to its resource
     * @return 200 response with resource body
     */
    public static <E, R> ResponseEntity<?> ok(E entity, Function<E, R> toResource) {
        return ResponseEntity.ok(toResource.apply(entity));
    }

    /**
     * Converts a list query result into a 200 response.
     *
     * @param entities list result from a query
     * @param toResource assembler from the aggregate to its resource
     * @return 200 response with resource list body
     */
    public static <E, R> ResponseEntity<?> okList(List<E> entities, Function<E, R> toResource) {
        return ResponseEntity.ok(entities.stream().map(toResource).toList());
    }

    /**
     * Builds a localized 400-response body.
     *
     * @param messageSource source for localized messages
     * @param messageKey message key to resolve
     * @param args optional interpolation values for localized messages
     * @return 400 response containing localized {@link ProblemDetail}
     */
    public static ResponseEntity<ProblemDetail> badRequest(MessageSource messageSource, String messageKey, Object... args) {
        return problemDetail(HttpStatus.BAD_REQUEST, messageSource, messageKey, args);
    }

    /**
     * Builds a localized 404-response body.
     *
     * @param messageSource source for localized messages
     * @param messageKey message key to resolve
     * @param args optional interpolation values for localized messages
     * @return 404 response containing localized {@link ProblemDetail}
     */
    public static ResponseEntity<ProblemDetail> notFound(MessageSource messageSource, String messageKey, Object... args) {
        return problemDetail(HttpStatus.NOT_FOUND, messageSource, messageKey, args);
    }

    private static ResponseEntity<ProblemDetail> problemDetail(HttpStatus status, MessageSource messageSource, String messageKey, Object... args) {
        var detail = messageSource.getMessage(messageKey, args, messageKey, LocaleContextHolder.getLocale());
        return ResponseEntity.status(status).body(ProblemDetail.forStatusAndDetail(status, detail));
    }
}

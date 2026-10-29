package com.ingescompany.doofplus.platform.subscriptions.application.internal.commandservices;

import com.ingescompany.doofplus.platform.subscriptions.application.commandservices.PlanCommandFailure;
import com.ingescompany.doofplus.platform.subscriptions.application.commandservices.PlanCommandService;
import com.ingescompany.doofplus.platform.subscriptions.domain.model.aggregates.Plan;
import com.ingescompany.doofplus.platform.subscriptions.domain.model.commands.CreatePlanCommand;
import com.ingescompany.doofplus.platform.subscriptions.infrastructure.persistence.jpa.PlanRepository;
import com.ingescompany.doofplus.platform.shared.application.result.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.ingescompany.doofplus.platform.subscriptions.domain.model.aggregates.Plan.PLAN_KEY_UNIQUE_CONSTRAINT;

/**
 * Application service for plan command operations.
 * Orchestrates the invariant that a plan key must be unique, returning an
 * application-layer failure instead of raising exceptions for duplicates.
 *
 * @since 1.0
 */
@Slf4j
@Service
public class PlanCommandServiceImpl implements PlanCommandService {
    private final PlanRepository planRepository;

    public PlanCommandServiceImpl(PlanRepository planRepository) {
        this.planRepository = planRepository;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional
    public Result<Plan, PlanCommandFailure> handle(CreatePlanCommand command) {
        if (planRepository.existsByPlanKey(command.planKey())) {
            log.warn("Duplicate plan detected (pre-check): planKey={}", command.planKey().value());
            return Result.failure(new PlanCommandFailure.Duplicate());
        }
        try {
            var createdPlan = planRepository.save(new Plan(command));
            log.info("Plan created: planKey={}, id={}", command.planKey().value(), createdPlan.getId());
            return Result.success(createdPlan);
        } catch (DataIntegrityViolationException exception) {
            if (isDuplicatePlanViolation(exception)) {
                log.warn("Duplicate plan detected (constraint violation): planKey={}", command.planKey().value());
                return Result.failure(new PlanCommandFailure.Duplicate());
            }
            log.error("Unexpected data integrity violation saving plan: planKey={}", command.planKey().value(), exception);
            throw exception;
        }
    }

    /**
     * Detects whether a data integrity violation corresponds to the plan key unique constraint.
     *
     * @param exception persistence exception raised during save
     * @return {@code true} when the exception chain contains the constraint name
     */
    private boolean isDuplicatePlanViolation(DataIntegrityViolationException exception) {
        Throwable violationCause = exception;
        while (violationCause != null) {
            String message = violationCause.getMessage();
            if (message != null && message.contains(PLAN_KEY_UNIQUE_CONSTRAINT)) return true;
            violationCause = violationCause.getCause();
        }
        return false;
    }
}

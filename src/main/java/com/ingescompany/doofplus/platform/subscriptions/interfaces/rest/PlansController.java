package com.ingescompany.doofplus.platform.subscriptions.interfaces.rest;

import com.ingescompany.doofplus.platform.subscriptions.application.commandservices.PlanCommandService;
import com.ingescompany.doofplus.platform.subscriptions.application.queryservices.PlanQueryService;
import com.ingescompany.doofplus.platform.subscriptions.domain.model.queries.GetAllPlansQuery;
import com.ingescompany.doofplus.platform.subscriptions.domain.model.queries.GetPlanByIdQuery;
import com.ingescompany.doofplus.platform.subscriptions.interfaces.rest.resources.CreatePlanResource;
import com.ingescompany.doofplus.platform.subscriptions.interfaces.rest.resources.PlanResource;
import com.ingescompany.doofplus.platform.subscriptions.interfaces.rest.transform.CreatePlanCommandFromResourceAssembler;
import com.ingescompany.doofplus.platform.subscriptions.interfaces.rest.transform.PlanResourceFromEntityAssembler;
import com.ingescompany.doofplus.platform.subscriptions.interfaces.rest.transform.ResponseEntityFromPlanCommandResultAssembler;
import com.ingescompany.doofplus.platform.subscriptions.interfaces.rest.transform.ResponseEntityFromSubscriptionsQueryResultAssembler;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.MessageSource;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

/**
 * Inbound service in the interface layer for subscription plans.
 * POST operations delegate to {@link PlanCommandService}; GET operations delegate to {@link PlanQueryService}.
 *
 * @since 1.0
 */
@Slf4j
@RestController
@RequestMapping(value = "/api/v1/plans", produces = APPLICATION_JSON_VALUE)
@Tag(name = "Plans", description = "Endpoints for subscription plans")
public class PlansController {
    private final PlanCommandService planCommandService;
    private final PlanQueryService planQueryService;
    private final MessageSource messageSource;

    public PlansController(PlanCommandService planCommandService, PlanQueryService planQueryService, MessageSource messageSource) {
        this.planCommandService = planCommandService;
        this.planQueryService = planQueryService;
        this.messageSource = messageSource;
    }

    /**
     * Creates a new plan.
     *
     * @param resource the request payload containing the plan data
     * @return the created plan resource, or conflict when the plan key already exists
     */
    @Operation(summary = "Create a plan", description = "Creates a subscription plan with its prices and limits")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Plan created",
                    content = @Content(schema = @Schema(implementation = PlanResource.class))),
            @ApiResponse(responseCode = "400", description = "Bad request",
                    content = @Content(schema = @Schema(implementation = ProblemDetail.class))),
            @ApiResponse(responseCode = "409", description = "Conflict - a plan with the same key already exists",
                    content = @Content(schema = @Schema(implementation = ProblemDetail.class)))
    })
    @PostMapping
    public ResponseEntity<?> createPlan(@Valid @RequestBody CreatePlanResource resource) {
        log.debug("POST /api/v1/plans - planKey={}", resource.planKey());
        var result = planCommandService.handle(CreatePlanCommandFromResourceAssembler.toCommandFromResource(resource));
        return ResponseEntityFromPlanCommandResultAssembler.toResponseEntityFromResult(result, messageSource);
    }

    /**
     * Gets all plans.
     *
     * @return list of plan resources
     */
    @Operation(summary = "Get all plans", description = "Gets the plans and prices shown to the laboratory administrator")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Plans found",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = PlanResource.class))))
    })
    @GetMapping
    public ResponseEntity<?> getAllPlans() {
        log.debug("GET /api/v1/plans");
        var plans = planQueryService.handle(new GetAllPlansQuery());
        return ResponseEntityFromSubscriptionsQueryResultAssembler.okList(plans, PlanResourceFromEntityAssembler::toResourceFromEntity);
    }

    /**
     * Gets a plan by ID.
     *
     * @param id Plan ID
     * @return the plan resource if found or not found otherwise
     */
    @Operation(summary = "Get a plan by ID", description = "Gets a plan by the provided ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Plan found",
                    content = @Content(schema = @Schema(implementation = PlanResource.class))),
            @ApiResponse(responseCode = "404", description = "Plan not found",
                    content = @Content(schema = @Schema(implementation = ProblemDetail.class)))
    })
    @GetMapping("{id}")
    public ResponseEntity<?> getPlanById(@PathVariable Long id) {
        log.debug("GET /api/v1/plans/{}", id);
        var plan = planQueryService.handle(new GetPlanByIdQuery(id));
        if (plan.isEmpty()) {
            return ResponseEntityFromSubscriptionsQueryResultAssembler.notFound(messageSource, "plan.error.notFoundById", id);
        }
        return ResponseEntityFromSubscriptionsQueryResultAssembler.ok(plan.get(), PlanResourceFromEntityAssembler::toResourceFromEntity);
    }
}

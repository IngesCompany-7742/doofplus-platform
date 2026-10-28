package com.ingescompany.doofplus.platform.subscriptions.infrastructure.persistence.jpa;

import com.ingescompany.doofplus.platform.subscriptions.domain.model.aggregates.Plan;
import com.ingescompany.doofplus.platform.subscriptions.domain.model.valueobjects.PlanKey;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository for querying and persisting {@link Plan} aggregates.
 * {@code existsByPlanKey()} checks the unique plan key invariant.
 *
 * @since 1.0
 */
@Repository
public interface PlanRepository extends JpaRepository<Plan, Long> {
    /**
     * Check if a plan exists by plan key.
     * @param planKey Plan key
     * @return True if exists, false otherwise
     */
    boolean existsByPlanKey(PlanKey planKey);
}

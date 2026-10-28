package com.ingescompany.doofplus.platform.subscriptions.infrastructure.persistence.jpa;

import com.ingescompany.doofplus.platform.subscriptions.domain.model.aggregates.Subscription;
import com.ingescompany.doofplus.platform.subscriptions.domain.model.valueobjects.OrganizationId;
import com.ingescompany.doofplus.platform.subscriptions.domain.model.valueobjects.SubscriptionStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;

/**
 * Repository for querying and persisting {@link Subscription} aggregates.
 * {@code existsByOrganizationIdAndStatusIn()} checks that an organization has a single current subscription.
 *
 * @since 1.0
 */
@Repository
public interface SubscriptionRepository extends JpaRepository<Subscription, Long> {
    /**
     * Find all subscriptions of an organization.
     * @param organizationId Organization ID
     * @return List of subscriptions
     */
    List<Subscription> findAllByOrganizationId(OrganizationId organizationId);

    /**
     * Check if an organization has a subscription in any of the given states.
     * @param organizationId Organization ID
     * @param statuses Subscription states
     * @return True if exists, false otherwise
     */
    boolean existsByOrganizationIdAndStatusIn(OrganizationId organizationId, Collection<SubscriptionStatus> statuses);
}

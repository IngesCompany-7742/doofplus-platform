package com.ingescompany.doofplus.platform.subscriptions.domain.model.aggregates;

import com.ingescompany.doofplus.platform.subscriptions.domain.model.commands.CreatePlanCommand;
import com.ingescompany.doofplus.platform.subscriptions.domain.model.valueobjects.BillingCycle;
import com.ingescompany.doofplus.platform.subscriptions.domain.model.valueobjects.Money;
import com.ingescompany.doofplus.platform.subscriptions.domain.model.valueobjects.PlanKey;
import com.ingescompany.doofplus.platform.subscriptions.infrastructure.persistence.jpa.converters.PlanKeyAttributeConverter;
import jakarta.persistence.*;
import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.Instant;

/**
 * Subscription plan offered by DoofPlus, with its prices and the user and IoT device limits.
 */
@Getter
@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(uniqueConstraints = {
        @UniqueConstraint(columnNames = { "plan_key" }, name = Plan.PLAN_KEY_UNIQUE_CONSTRAINT)
})
public class Plan {
    public static final String PLAN_KEY_UNIQUE_CONSTRAINT = "uk_plan_key";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    @Convert(converter = PlanKeyAttributeConverter.class)
    private PlanKey planKey;

    @Column(nullable = false)
    private String name;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "amount", column = @Column(name = "monthly_price_amount", nullable = false)),
            @AttributeOverride(name = "currency", column = @Column(name = "monthly_price_currency", nullable = false, length = 3))
    })
    private Money monthlyPrice;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "amount", column = @Column(name = "annual_price_amount", nullable = false)),
            @AttributeOverride(name = "currency", column = @Column(name = "annual_price_currency", nullable = false, length = 3))
    })
    private Money annualPrice;

    @Column(nullable = false)
    private int userLimit;

    @Column(nullable = false)
    private int iotDeviceLimit;

    @Column(nullable = false, updatable = false)
    @CreatedDate
    private Instant createdAt;

    @Column(nullable = false)
    @LastModifiedDate
    private Instant updatedAt;

    protected Plan() {}

    /**
     * Creates a plan aggregate from a create command.
     *
     * @param command command containing the plan data
     */
    public Plan(CreatePlanCommand command) {
        this.planKey = command.planKey();
        this.name = command.name();
        this.monthlyPrice = command.monthlyPrice();
        this.annualPrice = command.annualPrice();
        this.userLimit = command.userLimit();
        this.iotDeviceLimit = command.iotDeviceLimit();
    }

    /**
     * @param cycle billing cycle
     * @return price of the plan for the cycle
     */
    public Money getPrice(BillingCycle cycle) {
        return cycle == BillingCycle.MONTHLY ? monthlyPrice : annualPrice;
    }

    /**
     * Checks the plan limit before registering one more IoT device (US51, scenario 2).
     *
     * @param registeredDevices devices already registered by the organization
     * @return {@code true} when one more device fits in the plan
     */
    public boolean allowsOneMoreIotDevice(int registeredDevices) {
        return registeredDevices < iotDeviceLimit;
    }
}

package com.ingescompany.doofplus.platform.iam.domain.model.aggregates;

import com.ingescompany.doofplus.platform.iam.domain.model.commands.SignUpCommand;
import com.ingescompany.doofplus.platform.iam.domain.model.entities.Role;
import com.ingescompany.doofplus.platform.iam.domain.model.valueobjects.EmailAddress;
import com.ingescompany.doofplus.platform.iam.domain.model.valueobjects.OrganizationId;
import com.ingescompany.doofplus.platform.iam.infrastructure.persistence.jpa.converters.EmailAddressAttributeConverter;
import com.ingescompany.doofplus.platform.iam.infrastructure.persistence.jpa.converters.OrganizationIdAttributeConverter;
import jakarta.persistence.*;
import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.Instant;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * User of an organization (tenant) that signs in to DoofPlus with email address and password.
 */
@Getter
@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(uniqueConstraints = {
        @UniqueConstraint(columnNames = { "email_address" }, name = User.EMAIL_ADDRESS_UNIQUE_CONSTRAINT)
})
public class User {
    public static final String EMAIL_ADDRESS_UNIQUE_CONSTRAINT = "uk_user_email_address";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    @Convert(converter = EmailAddressAttributeConverter.class)
    private EmailAddress emailAddress;

    @Column(nullable = false)
    private String passwordHash;

    @Column(nullable = false)
    @Convert(converter = OrganizationIdAttributeConverter.class)
    private OrganizationId organizationId;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "user_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> roles = new HashSet<>();

    @Column(nullable = false, updatable = false)
    @CreatedDate
    private Instant createdAt;

    @Column(nullable = false)
    @LastModifiedDate
    private Instant updatedAt;

    protected User() {}

    /**
     * Creates a user aggregate from a sign-up command.
     *
     * @param command      command containing the user data
     * @param passwordHash password already hashed by the application layer
     * @param roles        persisted roles of the command; the default role when empty
     */
    public User(SignUpCommand command, String passwordHash, List<Role> roles) {
        this.emailAddress = command.emailAddress();
        this.passwordHash = passwordHash;
        this.organizationId = command.organizationId();
        addRoles(roles);
    }

    /**
     * Assigns a role to the user (US07).
     *
     * @param role role to assign
     * @return this user
     */
    public User addRole(Role role) {
        this.roles.add(role);
        return this;
    }

    /**
     * Assigns several roles to the user; the default role when the list is empty.
     *
     * @param roles roles to assign
     * @return this user
     */
    public User addRoles(List<Role> roles) {
        this.roles.addAll(Role.validateRoleSet(roles));
        return this;
    }

    /**
     * @param passwordHash new password, already hashed
     */
    public void changePassword(String passwordHash) {
        if (passwordHash == null || passwordHash.isBlank()) {
            throw new IllegalArgumentException("user.error.password.notBlank");
        }
        this.passwordHash = passwordHash;
    }
}

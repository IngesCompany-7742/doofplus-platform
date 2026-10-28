package com.ingescompany.doofplus.platform.iam.domain.model.entities;

import com.ingescompany.doofplus.platform.iam.domain.model.valueobjects.Roles;
import jakarta.persistence.*;
import lombok.Getter;

import java.util.List;

/**
 * Role that can be assigned to a user (US07).
 */
@Getter
@Entity
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, unique = true, length = 30)
    private Roles name;

    protected Role() {}

    public Role(Roles name) {
        this.name = name;
    }

    /**
     * @return name of the role as text, for example {@code ROLE_ADMIN}
     */
    public String getStringName() {
        return name.name();
    }

    /**
     * @return default role entity
     */
    public static Role getDefaultRole() {
        return new Role(Roles.getDefaultRole());
    }

    /**
     * @param name name of the role, for example {@code ROLE_ADMIN}
     * @return role entity
     */
    public static Role toRoleFromName(String name) {
        return new Role(Roles.valueOf(name));
    }

    /**
     * @param roles roles to validate
     * @return the same roles, or a list with the default role when it is empty
     */
    public static List<Role> validateRoleSet(List<Role> roles) {
        if (roles == null || roles.isEmpty()) {
            return List.of(getDefaultRole());
        }
        return roles;
    }
}

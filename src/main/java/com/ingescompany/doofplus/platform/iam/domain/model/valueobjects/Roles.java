package com.ingescompany.doofplus.platform.iam.domain.model.valueobjects;

/**
 * Roles that control access to the DoofPlus features (US07).
 */
public enum Roles {
    ROLE_ADMIN,
    ROLE_QUALITY_SPECIALIST,
    ROLE_PLANT_OPERATOR;

    /**
     * @return role assigned when none is given at sign-up
     */
    public static Roles getDefaultRole() {
        return ROLE_PLANT_OPERATOR;
    }
}

package com.ingescompany.doofplus.platform.organizations.domain.repository;

import com.ingescompany.doofplus.platform.organizations.domain.model.aggregates.Organization;
import com.ingescompany.doofplus.platform.organizations.domain.model.valueobjects.Ruc;

public interface OrganizationRepository {
    boolean existsByRuc(Ruc ruc);
    Organization save(Organization organization);
}
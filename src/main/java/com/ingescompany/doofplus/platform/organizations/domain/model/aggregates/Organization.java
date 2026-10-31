package com.ingescompany.doofplus.platform.organizations.domain.model.aggregates;

import com.ingescompany.doofplus.platform.organizations.domain.model.valueobjects.Ruc;

public class Organization {
    private Long id;
    private String legalName;
    private Ruc ruc;
    private String status;

    public Organization(String legalName, Ruc ruc) {
        if (legalName == null || legalName.isBlank()) {
            throw new IllegalArgumentException("El nombre legal de la organización no puede estar vacío.");
        }
        this.legalName = legalName;
        this.ruc = ruc;
        this.status = "ACTIVE"; // Por defecto según tu BD
    }

    public Organization(Long id, String legalName, Ruc ruc, String status) {
        this.id = id;
        this.legalName = legalName;
        this.ruc = ruc;
        this.status = status;
    }

    public Long getId() { return id; }
    public String getLegalName() { return legalName; }
    public Ruc getRuc() { return ruc; }
    public String getStatus() { return status; }
}
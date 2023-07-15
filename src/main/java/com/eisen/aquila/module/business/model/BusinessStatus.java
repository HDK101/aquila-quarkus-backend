package com.eisen.aquila.module.business.model;

import java.time.LocalTime;
import java.util.Optional;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.validation.constraints.NotNull;

@Entity(name = "business_status")
public class BusinessStatus extends PanacheEntity {
    private enum Status {
        CLOSED,
        OPEN
    }

    @NotNull
    @Column(name = "status", nullable = false)
    private Status status;

    public BusinessStatus() {
    }

    public BusinessStatus(@NotNull Status status) {
        this.status = status;
    }

    public BusinessStatus status(Status status) {
        this.status = status;
        return this;
    }

    public BusinessStatus put() {
        Optional<BusinessStatus> optionalBusinessStatus = findAll().firstResultOptional();

        if (optionalBusinessStatus.isPresent()) {
            var businessStatus = optionalBusinessStatus.get();
            businessStatus.status(status).persist();
            return businessStatus;
        }

        persist();
        return this;
    }
}

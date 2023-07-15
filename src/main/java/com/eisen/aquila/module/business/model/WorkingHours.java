package com.eisen.aquila.module.business.model;

import java.time.LocalTime;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;

@Entity(name = "working_hours")
public class WorkingHours extends PanacheEntity {
    @Column(name = "opens_in", nullable = false)
    private LocalTime opensIn;
    @Column(name = "closes_in", nullable = false)
    private LocalTime closesIn;
}

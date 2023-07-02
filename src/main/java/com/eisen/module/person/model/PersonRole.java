package com.eisen.module.person.model;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity(name = "Person_Role")
public class PersonRole extends PanacheEntity {
    @ManyToOne
    @JoinColumn(name="person_id", nullable=false)
    public Person person;
    
    @ManyToOne
    @JoinColumn(name="role_id", nullable=false)
    public Role role;
}

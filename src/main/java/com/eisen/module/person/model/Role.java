package com.eisen.module.person.model;

import java.util.List;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnore;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.NamedQueries;
import jakarta.persistence.NamedQuery;

@Entity
public class Role extends PanacheEntity {
    @Column(nullable = false, unique = true)
    public String nameDisplay;

    @Column(nullable = false, unique = true)
    public String nameId;


    @ManyToMany(mappedBy = "roles")
    @JsonIgnore
    public Set<Person> persons;
}

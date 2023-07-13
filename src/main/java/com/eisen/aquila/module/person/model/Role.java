package com.eisen.aquila.module.person.model;

import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnore;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;

@Entity(name = "roles")
@Table(name = "roles")
public class Role extends PanacheEntity {
    @Column(name = "name_display", nullable = false, unique = true)
    public String nameDisplay;

    @Column(name = "name_id", nullable = false, unique = true)
    public String nameId;


    @ManyToMany(mappedBy = "roles")
    @JsonIgnore
    public Set<Person> persons;

    public static Role findRoleByNameId(String nameId) {
        return find("nameId", nameId).firstResult();
    }
}

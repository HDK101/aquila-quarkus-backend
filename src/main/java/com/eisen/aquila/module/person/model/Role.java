package com.eisen.aquila.module.person.model;

import java.util.Optional;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnore;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.MappedSuperclass;
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

    public static Optional<Role> findRoleByNameIdOptional(String nameId) {
        return Optional.ofNullable(find("nameId", nameId).firstResult());
    }

    public Role nameDisplay(String nameDisplay) {
        this.nameDisplay = nameDisplay;
        return this;
    }

    public Role nameId(String nameId) {
        this.nameId = nameId;
        return this;
    }

    public Role persons(Set<Person> persons) {
        this.persons = persons;
        return this;
    }

    
}

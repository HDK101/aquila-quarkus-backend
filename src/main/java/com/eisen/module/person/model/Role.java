package com.eisen.module.person.model;

import java.util.List;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.NamedQueries;
import jakarta.persistence.NamedQuery;

@Entity
@NamedQueries(
    @NamedQuery(name = "Role.findByPersonId", query = "SELECT r.nameDisplay AS nameDisplay, r.nameId AS nameId FROM Role r INNER JOIN Person_Role")
)
public class Role extends PanacheEntity {
    @Column(nullable = false, unique = true)
    public String nameDisplay;

    @Column(nullable = false, unique = true)
    public String nameId;

    public static List<Role> findAllByPerson(Person person) {
        return find("#Role.findByPersonId", person.id).list();
    }
}

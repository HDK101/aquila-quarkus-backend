package com.eisen.module.person.model;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import io.quarkus.security.jpa.UserDefinition;
import io.quarkus.security.jpa.Username;
import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.NamedQueries;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;

@Entity(name = "persons")
@Table(name = "persons")
public class Person extends PanacheEntity {
    @Column(nullable = false)
    public String name;
    @Column(nullable = false)
    public LocalDate birth;
    @Column(nullable = false)
    public String login;
    @Column(nullable = false)
    public String email;
    @Column(nullable = false)
    public String phone;
    @Transient
    @JsonProperty(access = Access.WRITE_ONLY)
    public String password;
    @Column(name = "password_hash", nullable = false)
    @JsonIgnore
    public String passwordHash;

    @JoinTable(
        name = "person_role", 
        joinColumns = { @JoinColumn(name = "person_id", nullable = false) }, 
        inverseJoinColumns = { @JoinColumn(name = "role_id", nullable = false) }
    )
    @ManyToMany(cascade = { CascadeType.REMOVE }, fetch = FetchType.LAZY)
    public Set<Role> roles;

    @OneToMany(mappedBy = "person")
    @JsonIgnore
    public List<Session> sessions;

    public Person() {
    }

    public Person(String name, LocalDate birth) {
        this.name = name;
        this.birth = birth;
    }

    public static Person findByName(String name) {
        return find("name", name).firstResult();
    }

    public static Optional<Person> findByEmail(String email) {
        return Optional.ofNullable(
                find("email", email).firstResult());
    }

    public static Optional<Person> findByLogin(String login) {
        return Optional.ofNullable(
                find("login", login).firstResult());
    }
}

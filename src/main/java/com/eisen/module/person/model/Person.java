package com.eisen.module.person.model;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.hibernate.validator.constraints.Length;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;

import io.quarkus.elytron.security.common.BcryptUtil;
import io.quarkus.hibernate.orm.panache.PanacheEntity;
import io.quarkus.security.jpa.UserDefinition;
import io.quarkus.security.jpa.Username;
import jakarta.validation.constraints.NotNull;
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
    @NotNull
    @Column(nullable = false)
    public String name;
    @NotNull
    @Column(nullable = false)
    public LocalDate birth;
    @NotNull
    @Column(nullable = false, unique = true)
    public String login;
    @NotNull
    @Column(nullable = false)
    public String email;
    @NotNull
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

    public void hashPassword() {
        if (password != null) {
            passwordHash = BcryptUtil.bcryptHash(password, 8);
        }
    }
    
    @Override
    public void persist() {
        System.out.println("hashing password lol");
        hashPassword();
        super.persist();
    }

    

    @Override
    public void persistAndFlush() {
        System.out.println("hashing password lol flush");
        hashPassword();
        super.persistAndFlush();
    }

    public Set<String> getSetOfRolesNameId() {
        List<String> rolesList = roles.stream().map(i -> i.nameId).collect(Collectors.toList());
        Set<String> rolesNamesId = new HashSet<>(rolesList);

        return rolesNamesId;
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

    public Person name(String name) {
        this.name = name;
        return this;
    }

    public Person birth(LocalDate birth) {
        this.birth = birth;
        return this;
    }

    public Person login(String login) {
        this.login = login;
        return this;
    }

    public Person email(String email) {
        this.email = email;
        return this;
    }

    public Person phone(String phone) {
        this.phone = phone;
        return this;
    }

    public Person password(String password) {
        this.password = password;
        return this;
    }

    public Person passwordHash(String passwordHash) {
        this.passwordHash = passwordHash;
        return this;
    }

    public Person roles(Set<Role> roles) {
        this.roles = roles;
        return this;
    }

    public Person sessions(List<Session> sessions) {
        this.sessions = sessions;
        return this;
    }    
}

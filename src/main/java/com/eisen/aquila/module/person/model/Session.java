package com.eisen.aquila.module.person.model;

import java.util.List;

import com.eisen.aquila.module.person.exception.SessionNotFoundException;
import com.fasterxml.jackson.annotation.JsonIgnore;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity(name = "sessions")
@Table(name = "sessions")
public class Session extends PanacheEntity{

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "person_id", nullable = false)
    @JsonIgnore
    public Person person;

    public Session() {
    }

    public Session(Person person) {
        this.person = person;
    }

    public static List<Session> findByPersonId(Long personId) {
        return find("WHERE person_id = ?1", personId).list();
    }

    public static void destroyWithPersonId(Long sessionId, Long personId) {
        Long entitiesDeleted = delete("id = ?1 and person.id = ?2", sessionId, personId);

        if (entitiesDeleted == 0) throw new SessionNotFoundException("Session not found");
    }

    public static Long destroyAllByPersonId(Long personId) {
        return delete("person.id = ?1", personId);
    }
}

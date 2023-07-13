package com.eisen.aquila.module.person.service;

import org.eclipse.microprofile.jwt.Claim;
import org.eclipse.microprofile.jwt.JsonWebToken;

import com.eisen.aquila.module.person.model.Person;
import com.eisen.aquila.module.person.model.Session;

import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;

@RequestScoped
public class LoggedPersonService {
    @Inject
    JsonWebToken jsonWebToken;
    
    @Inject
    @Claim(value = "personId")
    Long personId;

    @Inject
    @Claim(value = "sessionId")
    Long sessionId;

    public Person authenticatedPerson() {
        Person person = (Person)Person.findByIdOptional(personId).orElseThrow();

        return person;
    }

    public Session currentSession() {
        Session session = (Session)Session.findByIdOptional(sessionId).orElseThrow();

        return session;
    }
}

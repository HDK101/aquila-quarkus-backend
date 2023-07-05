package com.eisen.module.person.resource;

import com.eisen.module.person.model.Person;

import io.quarkus.hibernate.orm.rest.data.panache.PanacheEntityResource;
import io.quarkus.rest.data.panache.ResourceProperties;
import jakarta.annotation.security.RolesAllowed;

@ResourceProperties(path = "persons", rolesAllowed = { "super_admin" })
public interface PersonResource extends PanacheEntityResource<Person, Long> {
    
}

package com.eisen.aquila.module.person.resource;

import com.eisen.aquila.module.person.model.Person;

import io.quarkus.hibernate.orm.rest.data.panache.PanacheEntityResource;
import io.quarkus.rest.data.panache.ResourceProperties;

@ResourceProperties(path = "persons", rolesAllowed = { "super_admin" })
public interface PersonResource extends PanacheEntityResource<Person, Long> {
    
}

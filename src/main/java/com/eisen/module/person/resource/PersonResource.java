package com.eisen.module.person.resource;

import com.eisen.module.person.model.Person;

import io.quarkus.hibernate.orm.rest.data.panache.PanacheEntityResource;
import io.quarkus.rest.data.panache.ResourceProperties;

@ResourceProperties(path = "persons")
public interface PersonResource extends PanacheEntityResource<Person, Long> {
    
}

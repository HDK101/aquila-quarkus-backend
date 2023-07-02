package com.eisen.module.person.resource;

import com.eisen.module.person.model.Role;

import io.quarkus.hibernate.orm.rest.data.panache.PanacheEntityResource;
import io.quarkus.rest.data.panache.ResourceProperties;

@ResourceProperties(path = "roles")
public interface RoleResource extends PanacheEntityResource<Role, Long> {
    
}

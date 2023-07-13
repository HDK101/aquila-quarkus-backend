package com.eisen.aquila.module.person.resource;

import com.eisen.aquila.module.person.model.Role;

import io.quarkus.hibernate.orm.rest.data.panache.PanacheEntityResource;
import io.quarkus.rest.data.panache.ResourceProperties;

@ResourceProperties(path = "roles", rolesAllowed = { "admin" })
public interface RoleResource extends PanacheEntityResource<Role, Long> {
    
}

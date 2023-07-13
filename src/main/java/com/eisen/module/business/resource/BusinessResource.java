package com.eisen.module.business.resource;

import com.eisen.module.business.model.BusinessStatus;

import jakarta.annotation.security.RolesAllowed;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.validation.Valid;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;

@Path("/business")
@ApplicationScoped
@RolesAllowed({ "admin", "super_admin" })
public class BusinessResource {
    @PUT
    @Path("/status")
    public BusinessStatus businessStatus(@Valid BusinessStatus businessStatus) {
        return businessStatus.put();
    }
}

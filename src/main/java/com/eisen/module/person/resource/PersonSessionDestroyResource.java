package com.eisen.module.person.resource;

import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.jboss.resteasy.reactive.RestPath;

import com.eisen.module.person.model.Session;
import com.eisen.module.person.service.LoggedPersonService;

import jakarta.annotation.security.RolesAllowed;
import jakarta.annotation.security.RolesAllowed;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.Response.Status;

@ApplicationScoped
@Path("/persons/sessions")
public class PersonSessionDestroyResource {
    @Inject 
    LoggedPersonService loggedPersonService;

    @DELETE
    @RolesAllowed({ "user" })
    @Transactional
    @APIResponse(
        responseCode = "201",
        description = "Succesfully created a person register"
    )
    @APIResponse(
        responseCode = "500",
        description = "Internal server error"
    )
    public Response destroyAll() {
        Long personId = loggedPersonService.authenticatedPerson().id;
        Session.destroyAllByPersonId(personId);

        return Response.status(Status.NO_CONTENT).build();
    }

    @DELETE
    @Transactional
    @APIResponse(
        responseCode = "201",
        description = "Succesfully created a person register"
    )
    @APIResponse(
        responseCode = "500",
        description = "Internal server error"
    )
    @RolesAllowed({ "user" })
    @Path("/{id}")
    public Response destroy(@RestPath Long id) {
        Long personId = loggedPersonService.authenticatedPerson().id;
        Session.destroyWithPersonId(id, personId);

        return Response.status(Status.NO_CONTENT).build();
    }
}

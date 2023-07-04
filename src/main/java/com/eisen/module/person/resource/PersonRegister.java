package com.eisen.module.person.resource;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.jboss.resteasy.reactive.ResponseStatus;

import com.eisen.module.person.dto.CreateRegister;
import com.eisen.module.person.model.Person;
import com.eisen.module.person.model.Role;

import io.netty.handler.codec.http.HttpResponseStatus;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.Response.Status;

@ApplicationScoped
@Path("/persons/register")
public class PersonRegister {
    private final String clientNameId = "client";

    @POST
    @Transactional
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @APIResponse(
        responseCode = "201",
        description = "Succesfully created a person register"
    )
    @APIResponse(
        responseCode = "400",
        description = "Invalid body"
    )
    public Response store(@Valid CreateRegister createRegister) {
        Person person = new Person();

        Role role = Role.findRoleByNameId(clientNameId);

        person
        .name(createRegister.name)
        .login(createRegister.email)
        .email(createRegister.email)
        .birth(createRegister.birth)
        .password(createRegister.password)
        .phone(createRegister.phone)
        .roles(new HashSet<Role>(List.of(role)));

        person.persistAndFlush();

        return Response.status(201).build();
    }
}

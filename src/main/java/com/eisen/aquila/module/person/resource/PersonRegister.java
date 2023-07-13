package com.eisen.aquila.module.person.resource;

import java.util.HashSet;
import java.util.List;

import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import com.eisen.aquila.module.person.dto.CreateRegister;
import com.eisen.aquila.module.person.model.Person;
import com.eisen.aquila.module.person.model.Role;

import io.smallrye.mutiny.Uni;
import io.smallrye.mutiny.infrastructure.Infrastructure;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

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
    public Uni<Response> store(@Valid CreateRegister createRegister) {
        Uni<Person> uniPerson = Uni.createFrom().item(() -> new Person()).onItem().transform(p -> {
            return p.name(createRegister.name)
            .login(createRegister.email)
            .email(createRegister.email)
            .birth(createRegister.birth)
            .password(createRegister.password)
            .phone(createRegister.phone);
        }).onItem().invoke(p -> {
            Role role = Role.findRoleByNameId(clientNameId);
            p.roles(new HashSet<Role>(List.of(role)));
        }).onItem().invoke(p -> {
            p.persistAndFlush();
        });

        return uniPerson.onItem().transform(p -> {
            return Response.status(201).build();
        });
    }
}

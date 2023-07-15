package com.eisen.aquila.module.person.resource;

import java.time.Duration;

import com.eisen.aquila.common.exception.DomainException;
import com.eisen.aquila.module.person.dto.CreateToken;
import com.eisen.aquila.module.person.exception.PersonTokenParseException;
import com.eisen.aquila.module.person.model.Person;
import com.eisen.aquila.module.person.model.Session;
import com.eisen.aquila.module.person.response.AccessTokenResponse;
import com.eisen.aquila.module.person.response.CreateSessionResponse;
import com.eisen.aquila.module.person.response.TokenResponse;
import com.eisen.aquila.module.person.service.LoggedPersonService;
import com.eisen.aquila.module.person.service.PersonService;

import io.smallrye.jwt.auth.principal.JWTParser;
import io.smallrye.jwt.auth.principal.ParseException;

import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.eclipse.microprofile.jwt.JsonWebToken;

import jakarta.annotation.security.RolesAllowed;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.Response.Status;

@ApplicationScoped
@Path("/persons/token")
public class PersonTokenResource {
    @Inject
    PersonService personService;

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response token(@Valid CreateToken createToken) {
        return Response.status(Status.CREATED).entity(personService.createToken(createToken)).build();
    }
}

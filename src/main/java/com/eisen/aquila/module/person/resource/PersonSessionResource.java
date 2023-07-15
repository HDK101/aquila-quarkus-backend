package com.eisen.aquila.module.person.resource;

import java.time.Duration;
import java.util.HashSet;

import org.eclipse.microprofile.config.inject.ConfigProperty;

import com.eisen.aquila.module.person.dto.CreateSession;
import com.eisen.aquila.module.person.exception.WrongPersonCredentialsException;
import com.eisen.aquila.module.person.model.Person;
import com.eisen.aquila.module.person.model.Session;
import com.eisen.aquila.module.person.response.CreateSessionResponse;

import java.util.List;

import io.quarkus.elytron.security.common.BcryptUtil;
import io.smallrye.jwt.build.Jwt;
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
@Path("/persons/sessions")
public class PersonSessionResource {
    @ConfigProperty(name = "mp.jwt.verify.issuer")
    private String issuer;

    private final Duration maxDuration = Duration.ofDays(7);

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Transactional
    public Response store(@Valid CreateSession createSession) {
        Person person = Person.findByEmail(createSession.login).orElseThrow(() -> { 
            throw new WrongPersonCredentialsException(400, "Credenciais incorretas");
        });

        if (!BcryptUtil.matches(createSession.password, person.passwordHash)) {
            throw new WrongPersonCredentialsException(400, "Credenciais incorretas");
        };

        Session session = new Session(person);

        session.persistAndFlush();

        String token = Jwt.issuer(issuer)
        .expiresIn(maxDuration)
        .upn(createSession.login)
        .claim("sessionId", session.id)
        .claim("personId", person.id)
        .claim("refresh", true)
        .sign();

        return Response.status(Status.CREATED).entity(new CreateSessionResponse(token)).build();
    }
}

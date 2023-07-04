package com.eisen.module.person.resource;

import java.time.Duration;
import java.util.Arrays;
import java.util.HashSet;

import org.eclipse.microprofile.config.inject.ConfigProperty;

import com.eisen.module.person.dto.CreateSession;
import com.eisen.module.person.exception.WrongPersonCredentialsException;
import com.eisen.module.person.model.Person;
import com.eisen.module.person.model.Session;
import com.eisen.module.person.response.CreateSessionResponse;

import io.quarkus.elytron.security.common.BcryptUtil;
import io.smallrye.jwt.build.Jwt;
import jakarta.annotation.security.PermitAll;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

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
    public CreateSessionResponse store(CreateSession createSession) {
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

        return new CreateSessionResponse(token);
    }
}

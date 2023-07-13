package com.eisen.aquila.module.person.resource;

import java.time.Duration;
import org.eclipse.microprofile.config.inject.ConfigProperty;

import com.eisen.aquila.module.person.model.Person;
import com.eisen.aquila.module.person.model.Session;
import com.eisen.aquila.module.person.response.AccessTokenResponse;
import com.eisen.aquila.module.person.service.LoggedPersonService;

import io.smallrye.jwt.build.Jwt;
import jakarta.annotation.security.RolesAllowed;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

@ApplicationScoped
@RolesAllowed({ "refresh" })
@Path("/persons/sessions/access-token")
public class PersonAccessTokenResource {
    @ConfigProperty(name = "mp.jwt.verify.issuer")
    private String issuer;

    private final Duration maxDuration = Duration.ofHours(1);

    @Inject
    LoggedPersonService loggedPersonService;

    @POST
    @RolesAllowed({ "refresh" })
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public AccessTokenResponse accessToken() {
        Person person = loggedPersonService.authenticatedPerson();
        Session session = loggedPersonService.currentSession();

        String token = Jwt.issuer(issuer)
        .expiresIn(maxDuration)
        .upn(person.email)
        .groups(person.getSetOfRolesNameId())
        .claim("sessionId", session.id)
        .claim("personId", person.id)
        .claim("access", true)
        .sign();

        return new AccessTokenResponse(token);
    }
}

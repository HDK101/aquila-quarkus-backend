package com.eisen.module.person.resource;

import java.time.Duration;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

import org.eclipse.microprofile.config.inject.ConfigProperty;

import com.eisen.module.person.model.Person;
import com.eisen.module.person.model.Role;
import com.eisen.module.person.model.Session;
import com.eisen.module.person.response.AccessTokenResponse;
import com.eisen.module.person.service.LoggedPersonService;

import io.smallrye.jwt.build.Jwt;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

@ApplicationScoped
@Path("/persons/sessions/access-token")
public class PersonAccessTokenResource {
    @ConfigProperty(name = "mp.jwt.verify.issuer")
    private String issuer;

    private final Duration maxDuration = Duration.ofHours(1);

    @Inject
    LoggedPersonService loggedPersonService;

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public AccessTokenResponse accessToken() {
        Person person = loggedPersonService.authenticatedPerson();
        Session session = loggedPersonService.currentSession();

        List<Role> roles = Role.findAllByPerson(person);
        System.out.println(roles);

        String token = Jwt.issuer(issuer)
        .expiresIn(maxDuration)
        .upn(person.email)
        .groups(new HashSet<>(Arrays.asList("User", "Admin")))
        .claim("sessionId", session.id)
        .claim("personId", person.id)
        .claim("access", true)
        .sign();

        return new AccessTokenResponse(token);
    }
}

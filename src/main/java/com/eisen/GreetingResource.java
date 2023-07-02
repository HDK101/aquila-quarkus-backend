package com.eisen;

import java.time.LocalDate;

import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.jboss.resteasy.reactive.server.spi.ServerRequestContext;

import com.eisen.module.person.model.Person;
import com.eisen.module.person.service.LoggedPersonService;

import io.quarkus.resteasy.reactive.server.runtime.ResteasyReactiveSecurityContext;
import io.quarkus.runtime.configuration.ProfileManager;
import io.quarkus.security.identity.SecurityIdentity;
import io.vertx.mutiny.core.http.HttpServerRequest;
import io.vertx.mutiny.core.http.HttpServerResponse;
import io.vertx.mutiny.ext.web.RoutingContext;
import jakarta.annotation.security.PermitAll;
import jakarta.annotation.security.RolesAllowed;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.interceptor.InvocationContext;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.container.ResourceContext;
import jakarta.ws.rs.container.ResourceInfo;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.SecurityContext;

@ApplicationScoped
@Path("/hello")
public class GreetingResource {
    @Inject
    LoggedPersonService loggedPersonService;

    @ConfigProperty(name = "quarkus.profile")
    public String profile;

    @GET
    @RolesAllowed({ "Walter" })
    @Produces(MediaType.APPLICATION_JSON)

    public String hello(@Context SecurityContext securityContext) {
        Person person = loggedPersonService.authenticatedPerson();

        System.out.println(person.name);
        return "{}";
    }
}

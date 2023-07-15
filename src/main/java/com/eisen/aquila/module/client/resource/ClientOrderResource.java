package com.eisen.aquila.module.client.resource;

import java.util.List;

import com.eisen.aquila.common.exception.JsonInternalErrorException;
import com.eisen.aquila.common.provider.MongoWrapperImpl;
import com.eisen.aquila.module.order.dto.CreateOrder;
import com.eisen.aquila.module.order.model.Order;
import com.eisen.aquila.module.order.service.OrderService;
import com.eisen.aquila.module.person.model.Person;
import com.eisen.aquila.module.person.service.LoggedPersonService;
import com.fasterxml.jackson.core.JacksonException;

import jakarta.annotation.security.RolesAllowed;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

@ApplicationScoped
@Path("/clients/orders")
@RolesAllowed({ "client" })
public class ClientOrderResource {
    @Inject MongoWrapperImpl mongoWrapper;

    @Inject LoggedPersonService loggedPersonService;

    @Inject OrderService orderService;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Order> orders() {
        Person person = loggedPersonService.authenticatedPerson();

        try {
            return mongoWrapper.findInCollection("orders", "person.id", person.id, Order.class);
        } catch (JacksonException ex) {
            throw new JsonInternalErrorException("JSON processing error");
        }
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Order store(@Valid CreateOrder createOrderBody) {
        return orderService.create(createOrderBody);
    }
}

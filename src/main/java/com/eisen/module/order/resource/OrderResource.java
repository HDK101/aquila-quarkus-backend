package com.eisen.module.order.resource;

import java.util.List;

import org.bson.Document;

import com.eisen.module.order.dto.CreateOrder;
import com.eisen.module.order.model.Order;
import com.eisen.module.order.service.OrderService;
import com.eisen.module.person.model.Person;
import com.eisen.module.product.model.Product;

import io.quarkus.security.identity.SecurityIdentity;
import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

@Path("/orders")
@RolesAllowed({ "client", "admin", "super_admin" })
public class OrderResource {
    @Inject
    SecurityIdentity securityIdentity;
    @Inject
    OrderService orderService;

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Document store(@Valid CreateOrder createOrderBody) {
        return orderService.create(createOrderBody.productIds);
    }

    @GET
    @RolesAllowed({ "admin", "super_admin" })
    @Produces(MediaType.APPLICATION_JSON)
    public List<Order> index() {
        return orderService.all();
    }
}

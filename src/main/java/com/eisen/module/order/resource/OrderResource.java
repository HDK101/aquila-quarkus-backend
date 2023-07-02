package com.eisen.module.order.resource;

import java.util.List;

import org.bson.Document;

import com.eisen.module.order.dto.CreateOrderBody;
import com.eisen.module.order.model.Order;
import com.eisen.module.order.service.OrderService;
import com.eisen.module.person.model.Person;
import com.eisen.module.product.model.Product;

import io.quarkus.security.identity.SecurityIdentity;
import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

@Path("/orders")
public class OrderResource {
    @Inject
    SecurityIdentity securityIdentity;
    @Inject
    OrderService orderService;

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Document store(CreateOrderBody createOrderBody) {
        Person person = new Person();
        person.id = 1L;

        Product product = new Product();
        product.id = 1L;
        product.customId = 23L;
        product.name = "Product";
        return orderService.create(person, List.of(product));
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Order> index() {
        return orderService.all();
    }
}

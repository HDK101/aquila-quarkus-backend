package com.eisen.module.order.resource;

import org.bson.Document;

import com.eisen.module.order.dto.CreateOrderBody;
import com.eisen.module.order.model.Order;
import com.eisen.module.order.service.OrderService;

import io.quarkus.security.identity.SecurityIdentity;
import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

@Path("/orders")
public class OrderResource {
    @Inject
    SecurityIdentity securityIdentity;
    @Inject
    OrderService orderService;

    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Document store(CreateOrderBody createOrderBody) {
        return orderService.create(null, null);
    }
}

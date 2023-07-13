package com.eisen.aquila.module.order.resource;

import java.util.List;

import org.jboss.resteasy.reactive.RestPath;

import com.eisen.aquila.module.order.dto.UpdateOrder;
import com.eisen.aquila.module.order.model.Order;
import com.eisen.aquila.module.order.service.OrderService;

import io.quarkus.security.identity.SecurityIdentity;
import jakarta.annotation.security.RolesAllowed;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.PATCH;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

@ApplicationScoped
@RolesAllowed({ "admin", "super_admin" })
@Path("/orders")
public class OrderResource {
    @Inject
    SecurityIdentity securityIdentity;
    @Inject
    OrderService orderService;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Order> index() {
        return orderService.all();
    }

    @PATCH
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Order update(@Valid UpdateOrder updateOrder, @RestPath String id) {
        return orderService.updateStatus(id, updateOrder.status);
    }
}

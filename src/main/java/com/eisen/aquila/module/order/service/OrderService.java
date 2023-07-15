package com.eisen.aquila.module.order.service;

import java.util.List;

import com.eisen.aquila.module.order.dto.CreateOrder;
import com.eisen.aquila.module.order.model.Order;
import com.eisen.aquila.module.order.model.Order.Status;

public interface OrderService {
    Order create(CreateOrder createOrder);

    Order updateStatus(String id, Status status);

    List<Order> all();
}
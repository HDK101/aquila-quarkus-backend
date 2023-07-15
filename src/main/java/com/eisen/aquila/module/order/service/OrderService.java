package com.eisen.aquila.module.order.service;

import java.util.List;

import org.bson.Document;

import com.eisen.aquila.common.util.Pair;
import com.eisen.aquila.module.order.dto.CreateOrder;
import com.eisen.aquila.module.order.model.Order;
import com.eisen.aquila.module.order.model.Order.Status;
import com.eisen.aquila.module.product.model.Product;

public interface OrderService {
    Order create(CreateOrder createOrder);

    Order updateStatus(String id, Status status);

    List<Order> all();
}
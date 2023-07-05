package com.eisen.module.order.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.bson.Document;

import com.eisen.module.order.dto.CreateOrder;
import com.eisen.module.order.exception.CreateOrderJsonException;
import com.eisen.module.order.model.Order;
import com.eisen.module.person.model.Person;
import com.eisen.module.person.service.LoggedPersonService;
import com.eisen.module.product.model.Product;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoCursor;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

@ApplicationScoped
public class OrderService {
    @Inject
    MongoClient mongoClient;

    @Inject
    ObjectMapper mapper;

    @Inject
    LoggedPersonService loggedPersonService;

    public Document create(List<CreateOrder.ProductSelection> productSelections) {
        Person person = loggedPersonService.authenticatedPerson();
        List<Long> productIds = productSelections.stream().map(productSelection -> productSelection.foodId)
                .collect(Collectors.toList());
        List<Product> products = Product.findIn(productIds);
        Order.Person oPerson = new Order.Person(person.id);

        Map<Long, Order.Product> orderProductMap = new HashMap<>();

        List<Order.Product> oProducts = new ArrayList<>();

        productSelections.forEach(product -> {
            orderProductMap.put(product.id, new Order.Product(product.id, product.name, product.customId, 1L));
        });

        Order order = new Order(oPerson, oProducts);

        try {

            Document document = Document.parse(mapper.writeValueAsString(order));

            mongoClient.getDatabase("aquila").getCollection("orders").insertOne(document);

            return document;
        } catch (JsonProcessingException ex) {
            System.out.println(ex.getMessage());
            return null;
        }
    }

    public List<Order> all() {
        MongoCursor<Document> cursor = mongoClient.getDatabase("aquila").getCollection("orders").find().iterator();

        List<Order> orders = new ArrayList<>();

        try {
            while (cursor.hasNext()) {
                Document document = cursor.next();

                document.toJson();

                Order order = mapper.readValue(document.toJson(), Order.class);
                orders.add(order);
            }
        } catch (JsonProcessingException ex) {
            CreateOrderJsonException createOrderJsonException = new CreateOrderJsonException(500, ex.getMessage(),
                    ex.getStackTrace());
            createOrderJsonException.setStackTrace(ex.getStackTrace());

            throw createOrderJsonException;
        } finally {
            cursor.close();
        }
        return orders;
    }
}

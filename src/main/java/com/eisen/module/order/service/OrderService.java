package com.eisen.module.order.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.bson.Document;

import com.eisen.common.util.Pair;
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

    private List<Long> retrieveIdsFromProductionSelects(List<CreateOrder.ProductSelection> productSelections) {
        return productSelections.stream().map(productSelection -> productSelection.foodId)
                .collect(Collectors.toList());
    }

    private Document writeOrderToDatabase(Order order) {
        try {
            Document document = Document.parse(mapper.writeValueAsString(order));

            mongoClient.getDatabase("aquila").getCollection("orders").insertOne(document);

            return document;
        } catch (JsonProcessingException ex) {
            System.out.println(ex.getMessage());
            return null;
        }
    }

    private List<Order.ProductJsonRepresentation> createRepresentationOfProducts(List<CreateOrder.ProductSelection> productSelections, Map<Long, Product> products) {
        List<Order.ProductJsonRepresentation> oProducts = new ArrayList<>();

        productSelections.forEach((productSelection) -> {
            Product product = products.get(productSelection.foodId);
            Order.ProductJsonRepresentation oProduct = Order.ProductJsonRepresentation.of(product, productSelection.quantity);
            oProducts.add(oProduct);
        });

        return oProducts;
    }

    public Document create(List<CreateOrder.ProductSelection> productSelections) {
        Person person = loggedPersonService.authenticatedPerson();
        List<Long> productIds = retrieveIdsFromProductionSelects(productSelections);
        Map<Long, Product> products = Product.findInRetrieveMap(productIds);

        Order.PersonDocumentRepresentation oPerson = Order.PersonDocumentRepresentation.of(person);
        List<Order.ProductJsonRepresentation> oProducts = createRepresentationOfProducts(productSelections, products);

        Order order = new Order(oPerson, oProducts);

        return writeOrderToDatabase(order);
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
            throwCreateOrderJsonException(ex);
        } finally {
            cursor.close();
        }
        return orders;
    }

    private void throwCreateOrderJsonException(JsonProcessingException exception) {
        CreateOrderJsonException createOrderJsonException = new CreateOrderJsonException(500, exception.getMessage(),
        exception.getStackTrace());
        createOrderJsonException.setStackTrace(exception.getStackTrace());

        throw createOrderJsonException;
    }
}

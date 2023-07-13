package com.eisen.aquila.module.order.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

import org.bson.Document;

import com.eisen.aquila.common.exception.JsonInternalErrorException;
import com.eisen.aquila.common.provider.MongoWrapper;
import com.eisen.aquila.module.order.dto.CreateOrder;
import com.eisen.aquila.module.order.exception.CreateOrderJsonException;
import com.eisen.aquila.module.order.model.Order;
import com.eisen.aquila.module.order.model.Order.Status;
import com.eisen.aquila.module.person.model.Person;
import com.eisen.aquila.module.person.service.LoggedPersonService;
import com.eisen.aquila.module.product.model.Product;
import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

@ApplicationScoped
public class OrderService {
    private static final String ORDERS_COLLECTION = "orders";

    @Inject
    MongoWrapper mongoWrapper;

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

            mongoWrapper.insertOneToCollection(ORDERS_COLLECTION, order);

            return document;
        } catch (JsonProcessingException ex) {
            throwCreateOrderJsonException(ex);
        }
        return null;
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

        Order order = new Order(oPerson, oProducts, Status.PENDING_CONFIRMATION);

        order.setId(UUID.randomUUID().toString());

        return writeOrderToDatabase(order);
    }

    public Order updateStatus(String id, Status status) {
        try {
            System.out.println(id);
            Order order = mongoWrapper.findInCollection(ORDERS_COLLECTION, "id", id, Order.class).get(0);
            order.setStatus(status);
            mongoWrapper.findAndReplaceTo(ORDERS_COLLECTION, "id", id, order);

            return order;
        } catch (JacksonException ex) {
            throw new JsonInternalErrorException("Json internal server error");
        }
    }

    public List<Order> all() {
        try {
            List<Order> orders = mongoWrapper.collectionMapTo(ORDERS_COLLECTION, Order.class);
            return orders;
        } catch (JsonProcessingException ex) {
            throwCreateOrderJsonException(ex);
        }
        return null;
    }

    private void throwCreateOrderJsonException(JsonProcessingException exception) {
        CreateOrderJsonException createOrderJsonException = new CreateOrderJsonException(500, exception.getMessage(),
        exception.getStackTrace());
        createOrderJsonException.setStackTrace(exception.getStackTrace());

        throw createOrderJsonException;
    }
}

package com.eisen.module.order.service;

import java.util.ArrayList;
import java.util.List;

import org.bson.Document;

import com.eisen.module.order.model.Order;
import com.eisen.module.person.model.Person;
import com.eisen.module.product.model.Product;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoCursor;

import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

@ApplicationScoped
public class OrderService {
    @Inject
    MongoClient mongoClient;

    public Document create(Person person, List<Product> products) {
        Document clientDocument = new Document().append("id", person.id);
        Document productsDocument = new Document();

        products.forEach(product -> {
            Document productDocument = new Document();
            productDocument.append("id", product.id);
            productDocument.append("name", product.name);

            if (product.promotionPriceInCents != null) {
                productDocument.append("priceInCents", product.priceInCents);
            }
            else {
                productDocument.append("priceInCents", product.promotionPriceInCents);
            }
        });

        Document document = new Document()
            .append("client", clientDocument)
            .append("products", productsDocument);

        mongoClient.getDatabase("aquila").getCollection("orders").insertOne(document);

        return document;
    }

    public List<Order> all() {
        MongoCursor<Document> cursor = mongoClient.getDatabase("aquila").getCollection("orders").find().iterator();

        List<Order> orders = new ArrayList<>();

        try {
            while (cursor.hasNext()) {
                Document document = cursor.next();
                Order order = new Order();

                Document clientDocument = (Document)document.get("client");
                System.out.println(clientDocument.getLong("id"));

                order.setPerson(new Order.Person(document.getLong("client.id")));

                orders.add(order);
            }
        } finally {
            cursor.close();
        }
        return orders;
    }
}

package com.eisen.module.order.service;

import java.util.List;

import org.bson.Document;

import com.eisen.module.order.model.Order;
import com.eisen.module.person.model.Person;
import com.eisen.module.product.model.Product;

import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class OrderService {
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

        

        return document;
    }
}

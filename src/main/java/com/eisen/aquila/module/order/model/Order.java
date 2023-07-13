package com.eisen.aquila.module.order.model;

import java.util.List;


import com.eisen.aquila.module.person.model.Person;
import com.eisen.aquila.module.product.model.Product;

public class Order {
    public static class PersonDocumentRepresentation {
        public Long id;

        public PersonDocumentRepresentation() {
        }

        public PersonDocumentRepresentation(Long id) {
            this.id = id;
        }

        public static PersonDocumentRepresentation of(Person person) {
            return new PersonDocumentRepresentation(person.id);
        }
    }

    public static class ProductJsonRepresentation {
        public Long id;
        public String name;
        public Long priceInCents;
        public Long quantity;

        public ProductJsonRepresentation() {
        }

        public ProductJsonRepresentation(Long id, String name, Long priceInCents, Long quantity) {
            this.id = id;
            this.name = name;
            this.priceInCents = priceInCents;
            this.quantity = quantity;
        }

        public ProductJsonRepresentation id(Long id) {
            this.id = id;
            return this;
        }

        public ProductJsonRepresentation name(String name) {
            this.name = name;
            return this;
        }

        public ProductJsonRepresentation priceInCents(Long priceInCents) {
            this.priceInCents = priceInCents;
            return this;
        }

        public ProductJsonRepresentation quantity(Long quantity) {
            this.quantity = quantity;
            return this;
        }

        public static ProductJsonRepresentation of(Product product, Long quantity) {
            return new ProductJsonRepresentation()
                    .id(product.id)
                    .name(product.name)
                    .priceInCents(product.priceInCents)
                    .quantity(quantity);
        }
    }

    public enum Status {
        PENDING_CONFIRMATION,
        IN_PROGRESS,
        DONE,
        CANCELLED
    }

    private String id;
    private Status status;

    private PersonDocumentRepresentation person;
    private List<ProductJsonRepresentation> products;

    public Order() {}

    public Order(PersonDocumentRepresentation person, List<ProductJsonRepresentation> products, Status status) {
        this.person = person;
        this.products = products;
        this.status = status;
    }

    public PersonDocumentRepresentation getPerson() {
        return person;
    }

    public void setPerson(PersonDocumentRepresentation person) {
        this.person = person;
    }

    public List<ProductJsonRepresentation> getProducts() {
        return products;
    }

    public void setProducts(List<ProductJsonRepresentation> products) {
        this.products = products;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}

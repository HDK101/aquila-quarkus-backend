package com.eisen.module.order.model;

import java.util.List;

public class Order {
    public static class Person {
        public Long id;

        public Person(Long id) {
            this.id = id;
        }
    }
    public static class Product {
        public Long id;
        public String name;
        public Long priceInCents;

        public Product(Long id, String name, Long priceInCents) {
            this.id = id;
            this.name = name;
            this.priceInCents = priceInCents;
        }
    }
    private Person person;
    private List<Product> products;

    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }

}

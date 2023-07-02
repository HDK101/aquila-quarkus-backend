package com.eisen.module.order.model;

import java.util.List;

import com.eisen.module.person.model.Person;
import com.eisen.module.product.model.Product;

public class Order {
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

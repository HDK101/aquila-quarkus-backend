package com.eisen.module.product.model;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;

@Entity
public class Product extends PanacheEntity {
    public Long id;
    public String name;
    public String description;
    public Long priceInCents;

    public Long promotionPriceInCents;

    @Column(unique = true)
    public Long customId;

    public Product() {
    }    
}

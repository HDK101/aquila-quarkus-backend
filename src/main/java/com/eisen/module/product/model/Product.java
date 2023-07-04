package com.eisen.module.product.model;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity(name = "products")
@Table(name = "products")
public class Product extends PanacheEntity {
    @Column(nullable = false)
    public String name;
    @Column(nullable = false)
    public String description;
    @Column(name = "price_in_cents", nullable = false)
    public Long priceInCents;
    
    @Column(name = "promotion_price_in_cents", nullable = false)
    public Long promotionPriceInCents;

    @Column(name = "custom_id", unique = true)
    public Long customId;

    public Product() {
    }    
}

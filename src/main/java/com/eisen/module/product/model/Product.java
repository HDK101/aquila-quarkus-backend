package com.eisen.module.product.model;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;

@Entity(name = "products")
@Table(name = "products")
public class Product extends PanacheEntity {
    @NotNull
    @Column(nullable = false)
    public String name;
    @NotNull
    @Column(nullable = false)
    public String description;
    @NotNull
    @Column(name = "price_in_cents", nullable = false)
    public Long priceInCents;
    
    @NotNull
    @Column(name = "promotion_price_in_cents", nullable = false)
    public Long promotionPriceInCents;
    
    @Column(name = "custom_id", unique = true)
    public Long customId;

    public Product() {
    }    
}

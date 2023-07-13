package com.eisen.aquila.module.product.model;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.eisen.aquila.module.product.exception.NotAllProductsFoundException;

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

    public static List<Product> findIn(List<Long> ids) {
        List<Product> products = find("where id in (?1)", ids).list();

        if (products.size() != ids.size()) {
            List<Long> notFoundProductIds = products.stream().filter(product -> ids.contains(product.id)).map(product -> product.id).collect(Collectors.toList());
            throw new NotAllProductsFoundException(notFoundProductIds, "Not all products were found by the IDs given");
        }

        return products;
    }

    public static Map<Long, Product> findInRetrieveMap(List<Long> ids) {
        List<Product> products = findIn(ids);
        
        HashMap<Long, Product> productMap = new HashMap<>();

        products.forEach(product -> {
            productMap.put(product.id, product);
        });

        return productMap;
    }
}

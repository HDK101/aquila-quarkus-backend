package com.eisen.module.product.resource;

import com.eisen.module.product.model.Product;

import io.quarkus.hibernate.orm.rest.data.panache.PanacheEntityResource;
import io.quarkus.rest.data.panache.ResourceProperties;

@ResourceProperties(path = "products", rolesAllowed = { "admin" }) 
public interface ProductResource extends PanacheEntityResource<Product, Long> {
    
}

package com.eisen.aquila.module.product.resource;

import com.eisen.aquila.module.product.model.Product;

import io.quarkus.hibernate.orm.rest.data.panache.PanacheEntityResource;
import io.quarkus.rest.data.panache.ResourceProperties;

@ResourceProperties(path = "products", rolesAllowed = { "admin" }) 
public interface ProductResource extends PanacheEntityResource<Product, Long> {
    
}

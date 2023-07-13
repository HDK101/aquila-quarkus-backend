package com.eisen.aquila.module.order.dto;

import java.util.List;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public class CreateOrder {
    public static class ProductSelection {
        @NotNull
        public Long foodId;
        @NotNull
        @Min(1)
        public Long quantity;

        public ProductSelection() {

        }
    }

    @NotNull
    public List<ProductSelection> productIds;
}

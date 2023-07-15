package com.eisen.aquila.module.order.dto;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import com.eisen.aquila.common.util.Pair;

import jakarta.validation.constraints.NotNull;

public class CreateOrder {
    @NotNull
    public List<ProductSelection> productSelections;

    public List<Long> getProductIds() {
        return productSelections.stream().map(p -> p.foodId).collect(Collectors.toList());
    }
}

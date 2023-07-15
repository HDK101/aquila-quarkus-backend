package com.eisen.aquila.module.order.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public class ProductSelection {
    @NotNull
    public Long foodId;
    @NotNull
    @Min(1)
    public Long quantity;
}
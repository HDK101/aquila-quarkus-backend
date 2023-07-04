package com.eisen.module.order.dto;

import java.util.List;

import jakarta.validation.constraints.NotNull;

public class CreateOrder {
    @NotNull
    public List<Long> foodIds;
}

package com.eisen.module.order.dto;

import com.eisen.module.order.model.Order.Status;

import jakarta.validation.constraints.NotNull;

public class UpdateOrder {
    @NotNull
    public Status status;
}

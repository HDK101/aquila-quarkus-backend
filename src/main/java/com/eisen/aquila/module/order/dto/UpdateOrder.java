package com.eisen.aquila.module.order.dto;

import com.eisen.aquila.module.order.model.Order.Status;

import jakarta.validation.constraints.NotNull;

public class UpdateOrder {
    @NotNull
    public Status status;
}

package com.eisen.module.order.exception;

import com.eisen.common.exception.DomainException;

public class CreateOrderJsonException extends DomainException {
    public CreateOrderJsonException(Integer statusCode, String errorMessage, StackTraceElement[] stackTrace) {
        super(statusCode, errorMessage);
        this.setStackTrace(stackTrace);
    }
}

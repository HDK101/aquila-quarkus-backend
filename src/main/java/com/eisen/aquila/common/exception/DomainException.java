package com.eisen.aquila.common.exception;

public class DomainException extends RuntimeException {
    public Integer statusCode;

    public DomainException(Integer statusCode, String errorMessage) {
        super(errorMessage);
        this.statusCode = statusCode;
    }
}

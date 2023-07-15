package com.eisen.aquila.common.exception;

public class DomainException extends RuntimeException {
    public Integer statusCode;

    public DomainException(Integer statusCode, String errorMessage) {
        super(errorMessage);
        this.statusCode = statusCode;
    }

    public DomainException(Integer statusCode, String errorMessage, Throwable throwable) {
        super(errorMessage, throwable);
        this.statusCode = statusCode;
    }
}

package com.eisen.aquila.common.exception;

public class JsonInternalErrorException extends DomainException {
    public JsonInternalErrorException(String errorMessage) {
        super(500, errorMessage);
    }
}

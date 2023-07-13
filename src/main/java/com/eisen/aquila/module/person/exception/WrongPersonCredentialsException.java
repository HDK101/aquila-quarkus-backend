package com.eisen.aquila.module.person.exception;

import com.eisen.aquila.common.exception.DomainException;

public class WrongPersonCredentialsException extends DomainException {
    public WrongPersonCredentialsException(Integer statusCode, String errorMessage) {
        super(statusCode, errorMessage);
    }
}

package com.eisen.module.person.exception;

import com.eisen.common.exception.DomainException;

public class WrongPersonCredentialsException extends DomainException {
    public WrongPersonCredentialsException(Integer statusCode, String errorMessage) {
        super(statusCode, errorMessage);
    }
}

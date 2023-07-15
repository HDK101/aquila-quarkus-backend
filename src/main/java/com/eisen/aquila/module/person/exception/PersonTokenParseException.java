package com.eisen.aquila.module.person.exception;

import com.eisen.aquila.common.exception.DomainException;

public class PersonTokenParseException extends DomainException {
    public PersonTokenParseException(String message, Throwable throwable) {
        super(500, message, throwable);
    }
}

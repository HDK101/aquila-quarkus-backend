package com.eisen.module.person.exception;

import com.eisen.common.exception.DomainException;

public class SessionNotFoundException extends DomainException {
    public SessionNotFoundException(String errorMessage) {
        super(404, errorMessage);
    }
}

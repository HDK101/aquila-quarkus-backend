package com.eisen.aquila.module.person.exception;

import com.eisen.aquila.common.exception.DomainException;

public class SessionNotFoundException extends DomainException {
    public SessionNotFoundException(String errorMessage) {
        super(404, errorMessage);
    }
}

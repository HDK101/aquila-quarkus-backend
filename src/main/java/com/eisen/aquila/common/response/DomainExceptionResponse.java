package com.eisen.aquila.common.response;

public class DomainExceptionResponse {
    public String message;
    public StackTraceElement[] stackTrace;
    
    public DomainExceptionResponse message(String message) {
        this.message = message;
        return this;
    }

    public DomainExceptionResponse stackTrace(StackTraceElement[] stackTrace) {
        this.stackTrace = stackTrace;
        return this;
    }
}

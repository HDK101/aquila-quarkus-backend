package com.eisen.module.person.provider;

import org.hibernate.exception.ConstraintViolationException;

import com.eisen.common.response.DomainExceptionResponse;

import io.quarkus.runtime.LaunchMode;
import io.quarkus.runtime.configuration.ProfileManager;
import jakarta.annotation.Priority;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

@Provider
@Priority(2)
public class PersonConstraintViolationExceptionMapper implements ExceptionMapper<ConstraintViolationException> {

    @Override
    public Response toResponse(ConstraintViolationException exception) {
        DomainExceptionResponse domainExceptionResponse = new DomainExceptionResponse()
                .message("Person login already exists");

        if (ProfileManager.getLaunchMode() == LaunchMode.DEVELOPMENT) {
            domainExceptionResponse.stackTrace(exception.getStackTrace());
        }

        return Response.status(400)
                .entity(domainExceptionResponse)
                .build();
    }

}

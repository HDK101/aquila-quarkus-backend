package com.eisen.common.provider;

import java.time.LocalDate;

import com.eisen.common.exception.DomainException;
import com.eisen.common.response.DomainExceptionResponse;

import io.quarkus.runtime.LaunchMode;
import io.quarkus.runtime.configuration.ProfileManager;
import jakarta.annotation.Priority;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

@Provider
@Priority(1)
public class DomainExceptionMapper implements ExceptionMapper<DomainException> {

    @Override
    public Response toResponse(DomainException exception) {
        DomainExceptionResponse domainExceptionResponse = new DomainExceptionResponse()
            .message(exception.getMessage());
           
        if (ProfileManager.getLaunchMode() == LaunchMode.DEVELOPMENT) {
            domainExceptionResponse.stackTrace(exception.getStackTrace());
        }
        
        return Response.status(exception.statusCode)
        .entity(domainExceptionResponse)
        .build();
    }
    
}

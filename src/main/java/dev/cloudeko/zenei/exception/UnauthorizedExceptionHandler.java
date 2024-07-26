package dev.cloudeko.zenei.exception;

import io.quarkus.security.UnauthorizedException;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;

public class UnauthorizedExceptionHandler implements ExceptionMapper<UnauthorizedException> {
    public UnauthorizedExceptionHandler() {
    }

    public Response toResponse(UnauthorizedException exception) {
        return Response.status(Response.Status.UNAUTHORIZED).entity(exception.getMessage()).build();
    }
}


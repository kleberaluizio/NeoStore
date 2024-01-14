package br.com.neostore.util;

import jakarta.validation.ConstraintViolationException;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

@Provider
public class ConstraintViolationExceptionMapper implements ExceptionMapper<ConstraintViolationException> {

    @Override
    public Response toResponse(ConstraintViolationException exception) {
        StringBuilder message = new StringBuilder("Erros de validação:  \n\n");
        exception.getConstraintViolations().forEach(violation ->
                message.append(violation.getMessage()).append("; \n"));

        return Response.status(Response.Status.BAD_REQUEST)
                .entity(message.toString())
                .build();
    }
}
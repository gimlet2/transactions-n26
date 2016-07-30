package de.number26.transactions.mapper;

import de.number26.transactions.exceptions.IllegalTransactionDataException;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class IllegalTransactionDataExceptionMapper implements ExceptionMapper<IllegalTransactionDataException> {
    @Override
    public Response toResponse(IllegalTransactionDataException e) {
        return Response.status(Response.Status.FORBIDDEN).entity(e.getMessage()).build();
    }
}

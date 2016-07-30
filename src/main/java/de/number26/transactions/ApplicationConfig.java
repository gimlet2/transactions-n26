package de.number26.transactions;

import de.number26.transactions.mapper.TransactionNotFoundExceptionMapper;
import org.glassfish.jersey.server.ResourceConfig;

import javax.ws.rs.ApplicationPath;

@ApplicationPath("/")
public class ApplicationConfig extends ResourceConfig {
    public ApplicationConfig() {
        packages("de.number26.transactions");
        register(TransactionResource.class);
        register(TransactionNotFoundExceptionMapper.class);
    }

}
package de.number26.transactions;

import de.number26.transactions.dto.Sum;
import de.number26.transactions.dto.Transaction;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.List;
import java.util.stream.Collectors;

@Path("/transactionservice")
@Produces(MediaType.APPLICATION_JSON)
public class TransactionResource {

    private final TransactionController transactionController;

    @Inject
    public TransactionResource(final TransactionController transactionController) {
        this.transactionController = transactionController;
    }

    @PUT
    @Path("/transaction/{id}")
    public Transaction createTransaction(@PathParam("id") Long id, Transaction transaction) {
        return transactionController.createTransaction(id, transaction);
    }

    @GET
    @Path("/transaction/{id}")
    public Transaction getTransactionById(@PathParam("id") Long id) {
        return transactionController.getTransactionById(id);
    }

    @GET
    @Path("/transaction/sum/{id}")
    public Sum getTransactionSumById(@PathParam("id") Long id) {
        return transactionController.calculateSumForTransaction(id);
    }

    @GET
    @Path("/types/{type}")
    public List<Long> getTransactionsByType(@PathParam("type") String type) {
        return transactionController.getTransactionsByType(type)
                .stream()
                .map(Transaction::getId)
                .collect(Collectors.toList());
    }
}

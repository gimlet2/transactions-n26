package de.number26.transactions.exceptions;

public class TransactionNotFoundException extends RuntimeException {


    public TransactionNotFoundException(Long id) {
        super("Transaction with id: " + id + " does not exist");
    }
}

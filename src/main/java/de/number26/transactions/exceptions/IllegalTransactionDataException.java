package de.number26.transactions.exceptions;

public class IllegalTransactionDataException extends RuntimeException {

    public IllegalTransactionDataException(String message) {
        super(message);
    }

    public IllegalTransactionDataException() {
        super("Illegal transaction data");
    }
}

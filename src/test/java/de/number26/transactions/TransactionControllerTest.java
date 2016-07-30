package de.number26.transactions;

import de.number26.transactions.dto.Sum;
import de.number26.transactions.dto.Transaction;
import de.number26.transactions.exceptions.IllegalTransactionDataException;
import de.number26.transactions.exceptions.TransactionNotFoundException;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.expectThrows;

public class TransactionControllerTest {

    public static final String TYPE = "SomeType";
    public static final String OTHER_TYPE = "OtherType";
    private static TransactionController transactionController;
    private static Transaction transaction2;

    @BeforeAll
    public static void prepareData() {
        transactionController = new TransactionController();
        Transaction transaction1 = transactionController.createTransaction(1L, new Transaction(2.0, TYPE, null));
        transaction2 = transactionController.createTransaction(2L, new Transaction(4.3, TYPE, transaction1.getId()));
        Transaction transaction3 = transactionController.createTransaction(3L, new Transaction(5.2, TYPE, transaction2.getId()));
        Transaction transaction4 = transactionController.createTransaction(4L, new Transaction(6.7, OTHER_TYPE, transaction3.getId()));
        Transaction transaction5 = transactionController.createTransaction(5L, new Transaction(7.2, OTHER_TYPE, transaction4.getId()));

    }

    @Test
    public void calculateSumForTransaction_forTwoTransactions() {
        // setup

        // act
        Sum sum = transactionController.calculateSumForTransaction(2L);

        // verify
        assertEquals(6.3, sum.getSum());
    }

    @Test
    public void calculateSumForTransaction_forOneTransactions() {
        // setup

        // act
        Sum sum = transactionController.calculateSumForTransaction(1L);

        // verify
        assertEquals(2.0, sum.getSum());
    }

    @Test
    public void calculateSumForTransaction_forFiveTransactions() {
        // setup

        // act
        Sum sum = transactionController.calculateSumForTransaction(5L);

        // verify
        assertEquals(25.4, sum.getSum());
    }

    @Test
    public void creatingTransactionWithTheSameIdForbidden() {
        // setup
        transactionController.createTransaction(6L, new Transaction(7.2, OTHER_TYPE, null));

        // act
        Throwable exception = expectThrows(IllegalTransactionDataException.class, () -> {
            transactionController.createTransaction(6L, new Transaction(7.2, OTHER_TYPE, null));
        });

        // verify
        assertEquals("Transaction with id 6 already exists", exception.getMessage());
    }

    @Test
    public void creatingTransactionWithNonExistedParentId() {
        // setup

        // act
        Throwable exception = expectThrows(TransactionNotFoundException.class, () -> {
            transactionController.createTransaction(8L, new Transaction(7.2, OTHER_TYPE, 99L));
        });

        // verify
        assertEquals("Transaction with id: 99 does not exist", exception.getMessage());
    }

    @Test
    public void getTransactionById() {
        // setup

        // act
        Transaction result = transactionController.getTransactionById(2L);

        // verify
        assertEquals((Long) 2L, result.getId());
        assertEquals(transaction2.getParentId(), result.getParentId());
        assertEquals(transaction2.getAmount(), result.getAmount());
        assertEquals(transaction2.getType(), result.getType());
    }

    @Test
    public void getTransactionById_transactionDoesNotExist() {
        // setup

        // act
        Throwable exception = expectThrows(TransactionNotFoundException.class, () -> {
            transactionController.getTransactionById(-2L);
        });

        // verify
        assertEquals("Transaction with id: -2 does not exist", exception.getMessage());
    }

    @Test
    public void getTransactionsByType() {
        // setup

        // act
        List<Transaction> result = transactionController.getTransactionsByType(TYPE);

        // verify
        assertEquals(3, result.size());
        assertEquals((Long) 1L, result.get(0).getId());
        assertEquals((Long) 2L, result.get(1).getId());
        assertEquals((Long) 3L, result.get(2).getId());
    }

    @Test
    public void getTransactionsByType_emptyResult() {
        // setup

        // act
        List<Transaction> result = transactionController.getTransactionsByType("someTypeThatDoesNotExist");

        // verify
        assertEquals(0, result.size());
    }
}

package de.number26.transactions;

import de.number26.transactions.dto.Sum;
import de.number26.transactions.dto.Transaction;
import de.number26.transactions.utils.MockitoExtension;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class TransactionResourceTest {

    private TransactionResource transactionResource;

    @BeforeEach
    public void setUp(@Mock TransactionController mockTransactionController) {
        transactionResource = new TransactionResource(mockTransactionController);
    }

    @Test
    public void getTransactionById(@Mock TransactionController mockTransactionController) {
        // setup
        long id = 32L;
        Transaction transaction = new Transaction();
        when(mockTransactionController.getTransactionById(id)).thenReturn(transaction);

        // act
        Transaction result = transactionResource.getTransactionById(id);

        // verify
        assertEquals(transaction, result);
    }

    @Test
    public void getTransactionsByType(@Mock TransactionController mockTransactionController) {
        // setup
        String type = "someType";
        List<Transaction> transactions = new ArrayList<>();
        transactions.add(new Transaction(1L));
        transactions.add(new Transaction(2L));
        transactions.add(new Transaction(5L));
        when(mockTransactionController.getTransactionsByType(type)).thenReturn(transactions);

        // act
        List<Long> result = transactionResource.getTransactionsByType(type);

        // verify
        assertEquals(3, result.size());
        assertEquals((Long) 1L, result.get(0));
        assertEquals((Long) 2L, result.get(1));
        assertEquals((Long) 5L, result.get(2));
    }

    @Test
    public void createTransaction(@Mock TransactionController mockTransactionController) {
        // setup
        long id = 32L;
        Transaction transaction = new Transaction();
        Transaction createdTransaction = new Transaction();
        when(mockTransactionController.createTransaction(id, transaction)).thenReturn(createdTransaction);

        // act
        Transaction result = transactionResource.createTransaction(id, transaction);

        // verify
        assertEquals(createdTransaction, result);

    }

    @Test
    public void getTransactionSumById(@Mock TransactionController mockTransactionController) {
        // setup
        long id = 35L;
        Sum sum = new Sum(3.5);
        when(mockTransactionController.calculateSumForTransaction(id)).thenReturn(sum);

        // act
        Sum result = transactionResource.getTransactionSumById(id);

        // verify
        assertEquals(sum, result);
    }
}
package de.number26.transactions;

import de.number26.transactions.dto.Sum;
import de.number26.transactions.dto.Transaction;
import de.number26.transactions.exceptions.IllegalTransactionDataException;
import de.number26.transactions.exceptions.TransactionNotFoundException;

import javax.inject.Singleton;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Singleton
public class TransactionController {

    private static final Map<Long, Transaction> transactions = new HashMap<>();

    public Transaction getTransactionById(Long id) {
        return transactions.computeIfAbsent(id, _id -> {
            throw new TransactionNotFoundException(_id);
        });
    }

    public List<Transaction> getTransactionsByType(String type) {
        return transactions.values()
                .stream()
                .filter(t -> t.getType().equals(type))
                .collect(Collectors.toList());
    }

    public Sum calculateSumForTransaction(Long id) {
        Transaction transaction = getTransactionById(id);
        Sum result = new Sum(transaction.getAmount());
        while (transaction.getParentId() != null) {
            transaction = getTransactionById(transaction.getParentId());
            result.add(transaction.getAmount());
        }
        return result;
    }

    public Transaction createTransaction(Long id, Transaction transaction) {
        transaction.setId(id);
        if (transactions.containsKey(id)) {
            throw new IllegalTransactionDataException("Transaction with id " + id + " already exists");
        }
        if (transaction.getParentId() != null && !transactions.containsKey(transaction.getParentId())) {
            throw new TransactionNotFoundException(transaction.getParentId());
        }
        transactions.put(id, transaction);
        return transaction;
    }

}

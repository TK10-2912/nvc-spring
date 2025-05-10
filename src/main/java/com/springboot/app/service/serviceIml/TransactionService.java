package com.springboot.app.service.serviceIml;

import com.springboot.app.entity.Transaction;
import com.springboot.app.repository.TransactionRepository;
import com.springboot.app.service.Iservice.ITransaction;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class TransactionService implements ITransaction {
    // Implement the methods defined in the ITransaction interface
    // For example:
    @Autowired
    private TransactionRepository transactionRepository;
    @Override
    public List<Transaction> getAllTransactions() {
         return transactionRepository.findAll();
    }

    @Override
    public Transaction getTransactionById(ObjectId id) {
        // Implementation here
        Transaction transaction = transactionRepository.findById(id).orElse(null);
        if (transaction != null) {
            return transaction;
        }
        return null;
    }

    @Override
    public void createTransaction(Transaction transaction) {
        // Implementation here
        transactionRepository.save(transaction);
    }

    @Override
    public void updateTransaction(ObjectId id, Transaction transaction) {
        // Implementation here
        Transaction existingTransaction = transactionRepository.findById(id).orElse(null);
        if (existingTransaction != null) {
            existingTransaction.setOrderCode(transaction.getOrderCode());
            existingTransaction.setMoney(transaction.getMoney());
            existingTransaction.setUnit(transaction.getUnit());
            existingTransaction.setAccountNumber(transaction.getAccountNumber());
            existingTransaction.setDepscription(transaction.getDepscription());
            existingTransaction.setCreatedAt(transaction.getCreatedAt());
            existingTransaction.setCodePaymentLink(transaction.getCodePaymentLink());
            existingTransaction.setReferenceCode(transaction.getReferenceCode());
            existingTransaction.setCodeBank(transaction.getCodeBank());
            existingTransaction.setBankName(transaction.getBankName());
            existingTransaction.setNameAccoutSend(transaction.getNameAccoutSend());
            existingTransaction.setAccoutNumberSend(transaction.getAccoutNumberSend());
            existingTransaction.setNameAccoutReal(transaction.getNameAccoutReal());
            existingTransaction.setAccoutNumberReal(transaction.getAccoutNumberReal());

            transactionRepository.save(existingTransaction);
        }
    }

    @Override
    public void deleteTransaction(ObjectId id) {
        // Implementation here
        Transaction existingTransaction = transactionRepository.findById(id).orElse(null);
        if (existingTransaction != null) {
            transactionRepository.delete(existingTransaction);
        }
    }
}
